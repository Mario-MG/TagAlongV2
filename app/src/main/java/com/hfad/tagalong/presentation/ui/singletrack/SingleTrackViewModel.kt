package com.hfad.tagalong.presentation.ui.singletrack

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.Session
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.presentation.ui.singletrack.SingleTrackEvent.*
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

    val track = mutableStateOf<Track?>(null)

    val tagsForTrack = mutableStateListOf<Tag>()

    val allTags = mutableStateListOf<Tag>() // TODO: These will be later used for autocompletion

    val loading = mutableStateOf(false)

    fun onTriggerEvent(event: SingleTrackEvent) {
        viewModelScope.launch {
            when (event) {
                is LoadTrackDetailsEvent -> {
                    loadTrack(event.trackId)
                }
                is AddTagEvent -> {
                    addTag(event.tagName)
                }
                is DeleteTagEvent -> {
                    deleteTag(event.tag)
                }
            }
            refreshTags()
        }
    }

    private suspend fun loadTrack(trackId: String) {
        loading.value = true
        val track = trackRepository.getTrack(
            token = session.getToken(),
            trackId = trackId
        )
        this.track.value = track // TODO: Handle null
        loading.value = false
    }

    private suspend fun addTag(tagName: String) {
        if (this.tagsForTrack.none { it.name == tagName }) {
            val existingTag = this.allTags.find { tag -> tag.name == tagName }
            if (existingTag != null) {
                trackTagRepository.addTagToTrack(tag = existingTag, track = track.value!!)
            } else {
                trackTagRepository.addTagToTrack(tag = Tag(name = tagName), track = track.value!!)
            }
        } else {
            // TODO: Show snack
        }
    }

    private suspend fun deleteTag(tag: Tag) {
        trackTagRepository.deleteTagFromTrack(tag = tag, track = track.value!!)
    }

    private suspend fun refreshTags() {
        getTagsForTrack(track.value!!)
        getAllTags()
    }

    private suspend fun getTagsForTrack(track: Track) {
        val tags = tagRepository.getAllForTrack(track).toMutableStateList()
        this.tagsForTrack.clear()
        this.tagsForTrack.addAll(tags)
    }

    private suspend fun getAllTags() {
        val allTags = tagRepository.getAll()
        this.allTags.clear()
        this.allTags.addAll(allTags)
    }

}