package com.hfad.tagalong.presentation.ui.tracks

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.BuildConfig
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.presentation.ui.tracks.PlaylistTracksEvent.*
import com.hfad.tagalong.repository.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistTracksViewModel
@Inject
constructor(
    private val trackRepository: TrackRepository,
    private val sessionManager: SessionManager
) : TracksViewModel() {

    val playlist = mutableStateOf<Playlist?>(null)

    override val screenTitle = mutableStateOf("")

    private var allTracksLoaded = false // TODO: Find out a way to improve this

    fun onTriggerEvent(event: PlaylistTracksEvent) {
        viewModelScope.launch {
            when (event) {
                is InitPlaylistTracksEvent -> {
                    init(event.playlist)
                    loadFirstPage()
                }
                is NextPageEvent -> {
                    loadNextPage()
                }
            }
        }
    }

    private suspend fun init(playlist: Playlist) {
        this.playlist.value = playlist
        this.screenTitle.value = playlist.name
        loadFirstPage()
    }

    private suspend fun loadFirstPage() {
        loading.value = true
        val tracks = trackRepository.getItemsInPlaylist(
            auth = sessionManager.getAuthorizationHeader(),
            playlist = this.playlist.value!!
        )
        this.tracks.clear()
        this.tracks.addAll(tracks)
        loading.value = false
    }

    private suspend fun loadNextPage() {
        if (!allTracksLoaded) {
            loading.value = true
            val currentListSize = this.tracks.size
            val newTracks = trackRepository.getItemsInPlaylist(
                auth = sessionManager.getAuthorizationHeader(),
                playlist = this.playlist.value!!,
                offset = currentListSize
            )
            if (newTracks.isEmpty()) {
                allTracksLoaded = true
            } else {
                this.tracks.addAll(newTracks)
            }
            loading.value = false
        }
    }

}