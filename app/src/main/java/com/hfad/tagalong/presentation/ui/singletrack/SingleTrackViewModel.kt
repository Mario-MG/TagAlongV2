package com.hfad.tagalong.presentation.ui.singletrack

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.presentation.ui.singletrack.SingleTrackEvent.*
import com.hfad.tagalong.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleTrackViewModel
@Inject
constructor(
    private val trackRepository: TrackRepository,
    private val sessionManager: SessionManager,
    private val trackTagRepository: TrackTagRepository,
    private val tagRepository: TagRepository,
    private val ruleRepository: RuleRepository,
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

    val track = mutableStateOf<Track?>(null)

    val tagsForTrack = mutableStateListOf<Tag>()

    val allTags = mutableStateListOf<Tag>()

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
            auth = sessionManager.getAuthorizationHeader(),
            trackId = trackId
        )
        this.track.value = track // TODO: Handle null?
        loading.value = false
    }

    private suspend fun addTag(tagName: String) {
        val tag = this.allTags.find { tag -> tag.name == tagName } ?: Tag(name = tagName)
        if (!this.tagsForTrack.contains(tag)) {
            trackTagRepository.addTagToTrack(tag = tag, track = track.value!!)
            applyRules(tag = tag, track = track.value!!)
        } else {
            // TODO: Show snack
        }
    }

    private suspend fun applyRules(tag: Tag, track: Track) {
        val rules = ruleRepository.getRulesFulfilledByTags(newTag = tag, originalTags = tagsForTrack)
        for (rule in rules) {
            playlistRepository.addTracksToPlaylist(
                auth = sessionManager.getAuthorizationHeader(),
                playlist = rule.playlist,
                tracks = listOf(track)
            )
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