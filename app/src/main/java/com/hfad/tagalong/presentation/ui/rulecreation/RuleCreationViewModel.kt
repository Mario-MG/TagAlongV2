package com.hfad.tagalong.presentation.ui.rulecreation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.interactors.tags.LoadAllTags
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.presentation.ui.rulecreation.RuleCreationEvent.*
import com.hfad.tagalong.repository.PlaylistRepository
import com.hfad.tagalong.repository.RuleRepository
import com.hfad.tagalong.repository.TrackRepository
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
    private val sessionManager: SessionManager,
    private val ruleRepository: RuleRepository,
    private val playlistRepository: PlaylistRepository,
    private val tracksRepository: TrackRepository
) : ViewModel() {

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
                    createRule()
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

                dataState.error?.let { error ->
                    // TODO
                }
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

    private suspend fun createRule() {
        if (isValidRule) {
            loading.value = true
            val newPlaylist = createPlaylist()
            val newRule = createRuleForPlaylist(newPlaylist)
            applyRule(newRule)
            finishedRuleCreation.value = true
            loading.value = false
        }
    }

    private suspend fun createPlaylist(): Playlist {
        return playlistRepository.create(
            auth = sessionManager.getAuthorizationHeader(),
            userId = sessionManager.getUser().id,
            playlist = Playlist(name = playlistName.value)
        )
    }

    private suspend fun createRuleForPlaylist(playlist: Playlist): Rule {
        val rule = Rule(
            playlist = playlist,
            optionality = optionality.value,
            autoUpdate = autoUpdate.value,
            tags = tags
        )
        ruleRepository.createNewRule(rule)
        return rule
    }

    private suspend fun applyRule(rule: Rule) {
        val tracks = if (rule.optionality) tracksRepository.getTracksWithAnyOfTheTags(tags) else tracksRepository.getTracksWithAllOfTheTags(tags)
        playlistRepository.addTracksToPlaylist(
            auth = sessionManager.getAuthorizationHeader(),
            playlist = rule.playlist,
            tracks = tracks
        )
    }

}