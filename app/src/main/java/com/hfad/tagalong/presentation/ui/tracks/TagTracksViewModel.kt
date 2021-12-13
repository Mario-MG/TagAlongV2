package com.hfad.tagalong.presentation.ui.tracks

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.presentation.ui.tracks.TagTracksEvent.InitTagTracksEvent
import com.hfad.tagalong.presentation.ui.tracks.TagTracksEvent.ReloadTagTracksEvent
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

    val tag = mutableStateOf<Tag?>(null)

    override val screenTitle = mutableStateOf("")

    fun onTriggerEvent(event: TagTracksEvent) {
        viewModelScope.launch {
            when (event) {
                is InitTagTracksEvent -> {
                    init(event.tag)
                }
                is ReloadTagTracksEvent -> {
                    loadTracks()
                }
            }
        }
    }

    private suspend fun init(tag: Tag) {
        this.tag.value = tag
        screenTitle.value = "#${tag.name}"
        loadTracks()
    }

    private suspend fun loadTracks() {
        loading.value = true
        val tracks = trackRepository.getTracksForTag(tag = tag.value!!)
        this.tracks.clear()
        this.tracks.addAll(tracks)
        loading.value = false
    }

}