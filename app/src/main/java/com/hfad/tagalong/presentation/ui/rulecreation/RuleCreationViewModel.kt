package com.hfad.tagalong.presentation.ui.rulecreation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.interactors.rulecreation.ApplyNewRule
import com.hfad.tagalong.interactors.rulecreation.CreatePlaylist
import com.hfad.tagalong.interactors.rulecreation.CreateRule
import com.hfad.tagalong.interactors.tags.LoadAllTags
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.presentation.ui.BaseViewModel
import com.hfad.tagalong.presentation.ui.rulecreation.RuleCreationEvent.*
import com.hfad.tagalong.presentation.util.DialogQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RuleCreationViewModel
@Inject
constructor(
    private val loadAllTags: LoadAllTags,
    private val createPlaylist: CreatePlaylist,
    private val createRule: CreateRule,
    private val applyNewRule: ApplyNewRule,
    private val sessionManager: SessionManager
) : BaseViewModel() {

    val loading = mutableStateOf(false)

    val playlistName = mutableStateOf(BaseApplication.getContext().getString(R.string.new_tagalong_playlist))

    val tags = mutableStateListOf<Tag>()

    val optionality = mutableStateOf(false)

    val autoUpdate = mutableStateOf(true)

    val allTags = mutableStateListOf<Tag>()

    val finishedRuleCreation = mutableStateOf(false)

    val isValidRule: Boolean
        get() {
            return tags.isNotEmpty() && playlistName.value.isNotBlank()
        }

    override val dialogQueue = DialogQueue()

    init {
        onTriggerEvent(InitRuleCreationEvent)
    }

    fun onTriggerEvent(event: RuleCreationEvent) {
        viewModelScope.launch {
            when (event) {
                is InitRuleCreationEvent -> {
                    getAllTags()
                }
                is ChangePlaylistNameEvent -> {
                    changePlaylistName(event.playlistName)
                }
                is AddTagEvent -> {
                    addTag(event.tagName)
                }
                is DeleteTagEvent -> {
                    deleteTag(event.tag)
                }
                is SwitchOptionalityEvent -> {
                    switchOptionality()
                }
                is SwitchAutoUpdateEvent -> {
                    switchAutoUpdate()
                }
                is CreateRuleEvent -> {
                    createPlaylistAndRule()
                }
            }
        }
    }

    private fun getAllTags() {
        loadAllTags
            .execute()
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { tags ->
                    this.allTags.clear()
                    this.allTags.addAll(tags)
                }

                dataState.error?.let(::appendGenericErrorToQueue)
            }
            .launchIn(viewModelScope)
    }

    private fun changePlaylistName(playlistName: String) {
        this.playlistName.value = playlistName
    }

    private fun addTag(tagName: String) {
        val existingTag = this.allTags.find { tag -> tag.name == tagName }
        existingTag?.let {
            tags.add(existingTag)
        }
    }

    fun canAddTag(tagName: String): Boolean = this.allTags.any { tag -> tag.name == tagName }
            && this.tags.none { tag -> tag.name == tagName }

    private fun deleteTag(tag: Tag) {
        tags.remove(tag)
    }

    private fun switchOptionality() {
        this.optionality.value = !this.optionality.value
    }

    private fun switchAutoUpdate() {
        this.autoUpdate.value = !this.autoUpdate.value
    }

    private fun createPlaylistAndRule() {
        createPlaylist
            .execute(
                auth = sessionManager.getAuthorizationHeader(),
                userId = sessionManager.user.id,
                playlistName = playlistName.value
            )
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { playlist ->
                    createRuleForPlaylist(playlist)
                }

                dataState.error?.let(::appendGenericErrorToQueue)
            }
            .launchIn(viewModelScope)
    }

    private fun createRuleForPlaylist(playlist: Playlist) {
        createRule
            .execute(
                playlist = playlist,
                optionality = optionality.value,
                autoUpdate = autoUpdate.value,
                tags = tags
            )
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { rule ->
                    applyRule(rule)
                }

                dataState.error?.let(::appendGenericErrorToQueue) // TODO: Remove newly created playlist
            }
            .launchIn(viewModelScope)
    }

    private fun applyRule(rule: Rule) {
        applyNewRule
            .execute(
                rule = rule,
                auth = sessionManager.getAuthorizationHeader()
            )
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let {
                    finishedRuleCreation.value = true
                }

                dataState.error?.let(::appendGenericErrorToQueue) // TODO: Undo rule and playlist creation?
            }
            .launchIn(viewModelScope)
    }

}