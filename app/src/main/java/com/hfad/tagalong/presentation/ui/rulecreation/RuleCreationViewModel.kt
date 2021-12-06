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
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

    val loading = mutableStateOf(false)

    val playlistName = mutableStateOf("New TagAlong Playlist")

    val tags = mutableStateListOf<Tag>()

    val optionality = mutableStateOf(false)

    val autoUpdate = mutableStateOf(true)

    private lateinit var allTags: List<Tag>

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
                    event.navController.popBackStack()
                }
            }
        }
    }

    private suspend fun getAllTags() {
        this.allTags = tagRepository.getAll()
    }

    private fun changePlaylistName(playlistName: String) {
        this.playlistName.value = playlistName
    }

    private fun addTag(tagName: String) { // TODO: This logic is for testing purposes only
        val existingTag = this.allTags.find { tag -> tag.name == tagName }
        existingTag?.let {
            tags.add(existingTag)
        }
    }

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
        loading.value = true
        val newPlaylist = playlistRepository.create(
            auth = session.getAuthorizationHeader(),
            userId = session.user!!.id,
            playlist = Playlist(name = playlistName.value)
        )
        val rule = Rule(
            playlistId = newPlaylist.id,
            optionality = optionality.value,
            autoUpdate = autoUpdate.value,
            tags = tags
        )
        ruleRepository.createNewRule(rule)
        loading.value = false
    }

}