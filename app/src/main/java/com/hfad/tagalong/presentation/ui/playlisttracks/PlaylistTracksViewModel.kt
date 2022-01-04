package com.hfad.tagalong.presentation.ui.playlisttracks

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.interactors.playlisttracks.LoadFirstPlaylistTracksPage
import com.hfad.tagalong.interactors.playlisttracks.LoadNextPlaylistTracksPage
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.presentation.ui.playlisttracks.PlaylistTracksEvent.*
import com.hfad.tagalong.presentation.ui.tracks.TracksViewModel
import com.hfad.tagalong.presentation.util.DialogQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistTracksViewModel
@Inject
constructor(
    private val loadFirstPlaylistTracksPage: LoadFirstPlaylistTracksPage,
    private val loadNextPlaylistTracksPage: LoadNextPlaylistTracksPage,
    private val sessionManager: SessionManager
) : TracksViewModel() {

    val playlist = mutableStateOf<Playlist?>(null)

    override val screenTitle = mutableStateOf("")

    override val dialogQueue = DialogQueue()

    private var firstPageLoaded = false // TODO: Find out a way to improve this

    private var allTracksLoaded = false // TODO: Find out a way to improve this

    fun onTriggerEvent(event: PlaylistTracksEvent) {
        viewModelScope.launch {
            when (event) {
                is InitPlaylistTracksEvent -> {
                    init(event.playlist)
                    if (!firstPageLoaded) {
                        loadFirstPage()
                    }
                }
                is NextPageEvent -> {
                    if (!allTracksLoaded) {
                        loadNextPage()
                    }
                }
            }
        }
    }

    private fun init(playlist: Playlist) {
        this.playlist.value = playlist
        this.screenTitle.value = playlist.name
    }

    private fun loadFirstPage() {
        loadFirstPlaylistTracksPage
            .execute(
                auth = sessionManager.getAuthorizationHeader(),
                playlist = this.playlist.value!!
            )
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { tracks ->
                    this.tracks.clear()
                    this.tracks.addAll(tracks)
                    firstPageLoaded = true
                }

                dataState.error?.let(::appendGenericErrorToQueue)
            }
            .launchIn(viewModelScope)
    }

    private fun loadNextPage() {
        val currentListSize = this.tracks.size
        loadNextPlaylistTracksPage
            .execute(
                auth = sessionManager.getAuthorizationHeader(),
                playlist = this.playlist.value!!,
                offset = currentListSize
            )
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { tracks ->
                    if (tracks.isEmpty()) {
                        allTracksLoaded = true
                    } else {
                        this.tracks.addAll(tracks)
                    }
                }

                dataState.error?.let(::appendGenericErrorToQueue) // FIXME: event is triggered endlessly
            }
            .launchIn(viewModelScope)
    }

}