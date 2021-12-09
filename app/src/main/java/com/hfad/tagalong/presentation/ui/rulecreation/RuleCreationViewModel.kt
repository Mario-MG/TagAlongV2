package com.hfad.tagalong.presentation.ui.rulecreation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.Session
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.presentation.ui.rulecreation.RuleCreationEvent.*
import com.hfad.tagalong.repository.PlaylistRepository
import com.hfad.tagalong.repository.RuleRepository
import com.hfad.tagalong.repository.TagRepository
import com.hfad.tagalong.repository.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RuleCreationViewModel
@Inject
constructor(
    private val session: Session,
    private val ruleRepository: RuleRepository,
    private val tagRepository: TagRepository,
    private val playlistRepository: PlaylistRepository,
    private val tracksRepository: TrackRepository
) : ViewModel() {

    val loading = mutableStateOf(false)

    val playlistName = mutableStateOf("New TagAlong Playlist")

    val tags = mutableStateListOf<Tag>()

    val optionality = mutableStateOf(false)

    val autoUpdate = mutableStateOf(true)

    val allTags = mutableStateListOf<Tag>()

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
                    createRuleAndExecuteCallback(event.callback)
                }
            }
        }
    }

    private suspend fun getAllTags() {
        val allTags = tagRepository.getAll()
        this.allTags.clear()
        this.allTags.addAll(allTags)
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

    private suspend fun createRuleAndExecuteCallback(callback: () -> Unit) {
        if (tags.isNotEmpty() && playlistName.value.isNotBlank()) {
            loading.value = true
            val newPlaylist = createPlaylist()
            val newRule = createRuleForPlaylist(newPlaylist)
            applyRule(newRule)
            callback()
            loading.value = false
        }
    }

    private suspend fun createPlaylist(): Playlist {
        return playlistRepository.create(
            auth = session.getAuthorizationHeader(),
            userId = session.user!!.id,
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
            auth = session.getAuthorizationHeader(),
            playlist = rule.playlist,
            tracks = tracks
        )
    }

}