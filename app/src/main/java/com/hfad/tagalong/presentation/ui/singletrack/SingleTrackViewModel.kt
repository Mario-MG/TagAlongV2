package com.hfad.tagalong.presentation.ui.singletrack

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.interactors.singletrack.CreateTag
import com.hfad.tagalong.interactors.singletrack.LoadTrackTags
import com.hfad.tagalong.interactors.tags.LoadAllTags
import com.hfad.tagalong.presentation.ui.singletrack.SingleTrackEvent.*
import com.hfad.tagalong.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleTrackViewModel
@Inject
constructor(
    private val loadAllTags: LoadAllTags,
    private val loadTrackTags: LoadTrackTags,
    private val createTag: CreateTag,
    private val sessionManager: SessionManager,
    private val trackTagRepository: TrackTagRepository,
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
                is InitTrackEvent -> {
                    init(event.track)
                    refreshTags()
                }
                is AddTagEvent -> {
                    addTag(event.tagName)
                }
                is DeleteTagEvent -> {
                    deleteTag(event.tag)
                    refreshTags() // TODO: Move this into "onSuccess"?
                }
            }
        }
    }

    private fun init(track: Track) {
        this.track.value = track
    }

    private suspend fun addTag(tagName: String) {
        createTag
            .execute(tagName = tagName)
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { tag ->
                    if (!this.tagsForTrack.contains(tag)) {
                        trackTagRepository.addTagToTrack(tag = tag, track = track.value!!)
                        applyRules(tag = tag, track = track.value!!)
                        refreshTags()
                    } else {
                        // TODO: Show snack
                    }
                }

                dataState.error?.let { error ->
                    // TODO
                }
            }
            .launchIn(viewModelScope)
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
        // TODO: Delete track from playlists where it doesn't fulfill the rules anymore
    }

    private fun refreshTags() {
        loadTagsForTrack()
        loadAllTags()
    }

    private fun loadTagsForTrack() {
        loadTrackTags
            .execute(track = track.value!!)
            .onEach { dataState ->
//                loading.value = dataState.loading // TODO: Handle loading "chaining"

                dataState.data?.let { tagsForTrack ->
                    this.tagsForTrack.clear()
                    this.tagsForTrack.addAll(tagsForTrack)
                }

                dataState.error?.let { error ->
                    // TODO
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadAllTags() {
        loadAllTags
            .execute()
            .onEach { dataState ->
//                loading.value = dataState.loading // TODO: Handle loading "chaining"

                dataState.data?.let { allTags ->
                    this.allTags.clear()
                    this.allTags.addAll(allTags)
                }

                dataState.error?.let { error ->
                    // TODO
                }
            }
            .launchIn(viewModelScope)
    }

}