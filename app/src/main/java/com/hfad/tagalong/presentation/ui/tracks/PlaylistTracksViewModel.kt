package com.hfad.tagalong.presentation.ui.tracks

import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.BuildConfig
import com.hfad.tagalong.Session
import com.hfad.tagalong.presentation.ui.tracks.PlaylistTracksEvent.FirstPageEvent
import com.hfad.tagalong.presentation.ui.tracks.PlaylistTracksEvent.NextPageEvent
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
    private val session: Session
) : TracksViewModel() {

    private var allTracksLoaded = false // TODO: Find out a way to improve this

    fun onTriggerEvent(event: PlaylistTracksEvent) {
        viewModelScope.launch {
            when (event) {
                is FirstPageEvent -> {
                    loadFirstPage(event.playlistId)
                }
                is NextPageEvent -> {
                    loadNextPage(event.playlistId)
                }
            }
        }
    }

    private suspend fun loadFirstPage(playlistId: String) {
        loading.value = true
        val tracks = trackRepository.getItemsInPlaylist(
            token = session.getToken(),
            playlistId = playlistId
        )
        this.tracks.clear()
        this.tracks.addAll(tracks)
        loading.value = false
    }

    private suspend fun loadNextPage(playlistId: String) {
        if (!allTracksLoaded) {
            loading.value = true
            val currentListSize = this.tracks.size
            val newTracks = trackRepository.getItemsInPlaylist(
                token = session.getToken(),
                playlistId = playlistId,
                offset = currentListSize
            )
            if (BuildConfig.DEBUG) delay(1000) // TODO: Remove
            if (newTracks.isEmpty()) {
                allTracksLoaded = true
            } else {
                this.tracks.addAll(newTracks)
            }
            loading.value = false
        }
    }

}