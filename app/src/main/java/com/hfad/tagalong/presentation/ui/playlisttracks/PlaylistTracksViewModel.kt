package com.hfad.tagalong.presentation.ui.playlisttracks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.interactors_core.util.on
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.presentation.ui.playlisttracks.PlaylistTracksEvent.InitPlaylistTracksEvent
import com.hfad.tagalong.presentation.ui.playlisttracks.PlaylistTracksEvent.NextPageEvent
import com.hfad.tagalong.presentation.ui.tracks.TracksViewModel
import com.hfad.tagalong.presentation.util.DialogQueue
import com.hfad.tagalong.track_domain.Track
import com.hfad.tagalong.track_interactors.LoadPlaylistTracksPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistTracksViewModel
@Inject
constructor(
    private val loadPlaylistTracksPage: LoadPlaylistTracksPage
) : TracksViewModel() {

    override var loading by mutableStateOf(false)

    override val tracks = mutableStateListOf<Track>()

    var playlist by mutableStateOf<Playlist?>(null)
        private set

    override var screenTitle by mutableStateOf("")

    override val dialogQueue = DialogQueue()

    private var nextPage = 0

    private var allTracksLoaded = false // TODO: Find out a way to improve this

    fun onTriggerEvent(event: PlaylistTracksEvent) {
        viewModelScope.launch {
            when (event) {
                is InitPlaylistTracksEvent -> {
                    init(event.playlist)
                    if (nextPage == 0) {
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
        this.playlist = playlist
        this.screenTitle = playlist.name
    }

    private fun loadFirstPage() {
        loadPlaylistTracksPage
            .execute(
                playlist = this.playlist!!
            )
            .on(
                loading = { loading = it },
                success = { tracks ->
                    this.tracks.clear()
                    this.tracks.addAll(tracks)
                    nextPage = 1
                },
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
    }

    private fun loadNextPage() {
        loadPlaylistTracksPage
            .execute(
                playlist = this.playlist!!,
                page = nextPage
            )
            .on(
                loading = { loading = it },
                success = { tracks ->
                    if (tracks.isEmpty()) {
                        allTracksLoaded = true
                    } else {
                        this.tracks.addAll(tracks)
                        nextPage += 1 // TODO: Make sure there are no synchronization issues
                    }
                },
                error = { error ->
                    allTracksLoaded = true // TODO: Improve this (its only purpose is to avoid the event being triggered in an infinite loop)
                    appendGenericErrorToQueue(error)
                }
            )
            .launchIn(viewModelScope)
    }

}