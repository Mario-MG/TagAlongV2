package com.hfad.tagalong.presentation.ui.singletrack

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
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
class SingleTrackViewModel
@Inject
constructor(
    private val trackRepository: TrackRepository,
    private val session: Session
) : ViewModel() {

    val track: MutableState<Track?> = mutableStateOf(null)
    val testTags = mutableStateListOf("rock", "pop", "80s", "testTag1", "testTag2", "testTag3",
        "testTag4", "testTag5", "testTag6", "testTag7", "testTag8", "testTag9", "testTag0") // TODO: This is for testing purposes only

    fun loadTrack(trackId: String) {
        viewModelScope.launch {
            getTrack(trackId)
        }
    }

    private suspend fun getTrack(trackId: String) {
        val track = trackRepository.getTrack(
            token = session.getToken(),
            trackId = trackId
        )
        this.track.value = track
    }

}