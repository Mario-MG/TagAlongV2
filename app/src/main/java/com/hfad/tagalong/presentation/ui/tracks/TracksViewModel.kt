package com.hfad.tagalong.presentation.ui.tracks

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.Session
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.repository.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TracksViewModel
@Inject
constructor(
    private val trackRepository: TrackRepository,
    private val session: Session
) : ViewModel() {

    val tracks: MutableState<List<Track>> = mutableStateOf(listOf())

    fun loadTracks(playlistId: String) {
        viewModelScope.launch {
            getTracksForPlaylistId(playlistId)
        }
    }

    private suspend fun getTracksForPlaylistId(playlistId: String) {
        val tracks = trackRepository.getItemsInPlaylist(
            token = session.getToken(),
            playlistId = playlistId
        )
        this.tracks.value = tracks
    }

}