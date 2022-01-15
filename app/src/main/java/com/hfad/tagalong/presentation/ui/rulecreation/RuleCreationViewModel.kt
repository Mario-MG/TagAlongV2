package com.hfad.tagalong.presentation.ui.rulecreation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.R
import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.interactors.data.on
import com.hfad.tagalong.interactors.rulecreation.ApplyNewRule
import com.hfad.tagalong.interactors.rulecreation.CreatePlaylist
import com.hfad.tagalong.interactors.rulecreation.CreateRule
import com.hfad.tagalong.interactors.tags.LoadAllTags
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.presentation.ui.BaseViewModel
import com.hfad.tagalong.presentation.ui.rulecreation.RuleCreationEvent.*
import com.hfad.tagalong.presentation.util.DialogQueue
import com.hfad.tagalong.tag_domain.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
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

    var loading by mutableStateOf(false)
        private set

    var playlistName by mutableStateOf(
        BaseApplication.getContext().getString(R.string.new_tagalong_playlist)
    )
        private set

    val tags = mutableStateListOf<Tag>()

    var optionality by mutableStateOf(false)
        private set

    var autoUpdate by mutableStateOf(true)
        private set

    val allTags = mutableStateListOf<Tag>()

    var finishedRuleCreation by mutableStateOf(false)
        private set

    val isValidRule: Boolean
        get() {
            return tags.isNotEmpty() && playlistName.isNotBlank()
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
            .on(
                loading = ::loading::set,
                success = { tags ->
                    this.allTags.clear()
                    this.allTags.addAll(tags)
                },
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
    }

    private fun changePlaylistName(playlistName: String) {
        this.playlistName = playlistName
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
        this.optionality = !this.optionality
    }

    private fun switchAutoUpdate() {
        this.autoUpdate = !this.autoUpdate
    }

    private fun createPlaylistAndRule() {
        createPlaylist
            .execute(
                auth = sessionManager.getAuthorizationHeader(),
                userId = sessionManager.user.id,
                playlistName = playlistName
            )
            .on(
                loading = ::loading::set,
                success = ::createRuleForPlaylist,
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
    }

    private fun createRuleForPlaylist(playlist: Playlist) {
        createRule
            .execute(
                playlist = playlist,
                optionality = optionality,
                autoUpdate = autoUpdate,
                tags = tags
            )
            .on(
                loading = ::loading::set,
                success = ::applyRule,
                error = ::appendGenericErrorToQueue // TODO: Remove newly created playlist
            )
            .launchIn(viewModelScope)
    }

    private fun applyRule(rule: Rule) {
        applyNewRule
            .execute(
                rule = rule,
                auth = sessionManager.getAuthorizationHeader()
            )
            .on(
                loading = ::loading::set,
                success = { finishedRuleCreation = true },
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
    }

}