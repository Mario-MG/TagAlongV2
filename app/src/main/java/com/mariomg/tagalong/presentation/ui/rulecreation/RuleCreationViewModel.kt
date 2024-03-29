package com.mariomg.tagalong.presentation.ui.rulecreation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.mariomg.tagalong.R
import com.mariomg.tagalong.interactors_core.util.on
import com.mariomg.tagalong.playlist_domain.Playlist
import com.mariomg.tagalong.playlist_domain.PlaylistInfo
import com.mariomg.tagalong.playlist_interactors.AddTracksToPlaylists
import com.mariomg.tagalong.playlist_interactors.CreatePlaylist
import com.mariomg.tagalong.presentation.BaseApplication
import com.mariomg.tagalong.presentation.ui.BaseViewModel
import com.mariomg.tagalong.presentation.ui.rulecreation.RuleCreationEvent.*
import com.mariomg.tagalong.presentation.util.DialogQueue
import com.mariomg.tagalong.rule_domain.Rule
import com.mariomg.tagalong.rule_domain.RuleInfo
import com.mariomg.tagalong.rule_interactors.CreateRule
import com.mariomg.tagalong.tag_domain.Tag
import com.mariomg.tagalong.tag_interactors.LoadAllTags
import com.mariomg.tagalong.track_domain.Track
import com.mariomg.tagalong.track_interactors.LoadTracksForRule
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
    private val loadTracksForRule: LoadTracksForRule,
    private val addTracksToPlaylists: AddTracksToPlaylists
) : BaseViewModel() {

    var loading by mutableStateOf(false)
        private set

    var playlistName by mutableStateOf(
        BaseApplication.getString(R.string.new_tagalong_playlist)
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
                playlistInfo = PlaylistInfo(
                    name = playlistName,
                    size = 0
                )
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
                RuleInfo(
                    playlist = playlist,
                    optionality = optionality,
                    autoUpdate = autoUpdate,
                    tags = tags
                )
            )
            .on(
                loading = ::loading::set,
                success = ::applyRule,
                error = ::appendGenericErrorToQueue // TODO: Remove newly created playlist
            )
            .launchIn(viewModelScope)
    }

    private fun applyRule(rule: Rule) {
        loadTracksForRule
            .execute(rule = rule)
            .on(
                loading = ::loading::set,
                success = { tracks ->
                    addTracksToPlaylist(
                        tracks = tracks,
                        playlist = rule.playlist
                    )
                },
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
    }

    private fun addTracksToPlaylist(tracks: List<Track>, playlist: Playlist) {
        addTracksToPlaylists
            .execute(
                tracks = tracks,
                playlists = listOf(playlist)
            )
            .on(
                loading = ::loading::set,
                success = { finishedRuleCreation = true },
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
    }

}