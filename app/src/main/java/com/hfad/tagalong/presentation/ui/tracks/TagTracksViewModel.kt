package com.hfad.tagalong.presentation.ui.tracks

import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.presentation.ui.tracks.TagTracksEvent.LoadTagTracksEvent
import com.hfad.tagalong.repository.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagTracksViewModel
@Inject
constructor(
    private val trackRepository: TrackRepository
) : TracksViewModel() {

    fun onTriggerEvent(event: TagTracksEvent) {
        viewModelScope.launch {
            when (event) {
                is LoadTagTracksEvent -> {
                    loadTracks(event.tagId)
                }
            }
        }
    }

    private suspend fun loadTracks(tagId: Long) {
        loading.value = true
        val tracks = trackRepository.getTracksForTag(
            tagId = tagId
        )
        this.tracks.clear()
        this.tracks.addAll(tracks)
        loading.value = false
    }

}