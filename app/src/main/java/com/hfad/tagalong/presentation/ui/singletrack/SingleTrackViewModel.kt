package com.hfad.tagalong.presentation.ui.singletrack

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.R
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.interactors.data.ErrorType.CacheError.DuplicateError
import com.hfad.tagalong.interactors.singletrack.*
import com.hfad.tagalong.interactors.tags.LoadAllTags
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.presentation.ui.BaseViewModel
import com.hfad.tagalong.presentation.ui.singletrack.SingleTrackEvent.*
import com.hfad.tagalong.presentation.util.DialogQueue
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
    private val addTagToTrack: AddTagToTrack,
    private val applyExistingRules: ApplyExistingRules,
    private val deleteTagFromTrack: DeleteTagFromTrack,
    private val sessionManager: SessionManager
) : BaseViewModel() {

    val track = mutableStateOf<Track?>(null)

    val tagsForTrack = mutableStateListOf<Tag>()

    val allTags = mutableStateListOf<Tag>()

    val loading = mutableStateOf(false)

    override val dialogQueue = DialogQueue()

    fun onTriggerEvent(event: SingleTrackEvent) {
        viewModelScope.launch {
            when (event) {
                is InitTrackEvent -> {
                    init(event.track)
                    refreshTags()
                }
                is AddTagEvent -> {
                    addTagByName(event.tagName)
                }
                is DeleteTagEvent -> {
                    deleteTag(event.tag)
                }
            }
        }
    }

    private fun init(track: Track) {
        this.track.value = track
    }

    private fun addTagByName(tagName: String) {
        createTag
            .execute(tagName = tagName)
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { tag ->
                    addTagToTrack(tag = tag)
                }

                dataState.error?.let(::appendGenericErrorToQueue)
            }
            .launchIn(viewModelScope)
    }

    private fun addTagToTrack(tag: Tag) {
        addTagToTrack
            .execute(tag = tag, track = track.value!!)
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let {
                    refreshTags()
                    applyRules(newTag = tag)
                }

                dataState.error?.let { error ->
                    when (error) {
                        is DuplicateError -> dialogQueue.appendInfoDialog(
                            BaseApplication.getContext().getString(R.string.duplicate_tag_info)
                        )
                        else -> appendGenericErrorToQueue(error)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun applyRules(newTag: Tag) {
        applyExistingRules
            .execute(
                newTag = newTag,
                originalTags = tagsForTrack,
                track = track.value!!,
                auth = sessionManager.getAuthorizationHeader()
            )
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.error?.let(::appendGenericErrorToQueue) // TODO: Undo add tag to track??
            }
            .launchIn(viewModelScope)
    }

    private fun deleteTag(tag: Tag) {
        deleteTagFromTrack
            .execute(
                tag = tag,
                track = track.value!!
            )
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let {
                    // TODO: Delete track from playlists where it doesn't fulfill the rules anymore
                    refreshTags()
                }

                dataState.error?.let(::appendGenericErrorToQueue)
            }
            .launchIn(viewModelScope)
    }

    private fun refreshTags() {
        loadTagsForTrack()
        loadAllTags()
    }

    private fun loadTagsForTrack() {
        loadTrackTags
            .execute(track = track.value!!)
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { tagsForTrack ->
                    this.tagsForTrack.clear()
                    this.tagsForTrack.addAll(tagsForTrack)
                }

                dataState.error?.let(::appendGenericErrorToQueue)
            }
            .launchIn(viewModelScope)
    }

    private fun loadAllTags() {
        loadAllTags
            .execute()
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { allTags ->
                    this.allTags.clear()
                    this.allTags.addAll(allTags)
                }

                dataState.error?.let(::appendGenericErrorToQueue)
            }
            .launchIn(viewModelScope)
    }

}