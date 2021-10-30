package com.hfad.tagalong.presentation.ui.singletrack

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.Session
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.repository.TagRepository
import com.hfad.tagalong.repository.TrackRepository
import com.hfad.tagalong.repository.TrackTagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleTrackViewModel
@Inject
constructor(
    private val trackRepository: TrackRepository,
    private val session: Session,
    private val trackTagRepository: TrackTagRepository,
    private val tagRepository: TagRepository
) : ViewModel() {

    val track: MutableState<Track?> = mutableStateOf(null)
    val tags = mutableStateListOf<Tag>()

    fun loadTrack(trackId: String) {
        viewModelScope.launch {
            getTrack(trackId)
            getTrackTags(track.value!!) // TODO: Handle null?
        }
    }

    fun addTag(tagName: String) {
        viewModelScope.launch {
            addNewTag(Tag(name = tagName))
            getTrackTags(track.value!!) // TODO: Handle null?
        }
    }

    private suspend fun getTrack(trackId: String) {
        val track = trackRepository.getTrack(
            token = session.getToken(),
            trackId = trackId
        )
        this.track.value = track
    }

    private suspend fun getTrackTags(track: Track) {
        val tags = tagRepository.getAllForTrack(track).toMutableStateList()
        this.tags.clear()
        this.tags.addAll(tags)
    }

    private suspend fun addNewTag(tag: Tag) {
        if (this.tags.none { it.name == tag.name }) {
            trackTagRepository.addTagToTrack(tag, track.value!!) // TODO: Handle null?
        } else {
            // TODO: Show snack
        }
    }

}