package com.mariomg.tagalong.presentation.ui.tagtracks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.mariomg.tagalong.interactors_core.util.on
import com.mariomg.tagalong.presentation.ui.tagtracks.TagTracksEvent.InitTagTracksEvent
import com.mariomg.tagalong.presentation.ui.tagtracks.TagTracksEvent.LoadTagTracksEvent
import com.mariomg.tagalong.presentation.ui.tracks.TracksViewModel
import com.mariomg.tagalong.presentation.util.DialogQueue
import com.mariomg.tagalong.tag_domain.Tag
import com.mariomg.tagalong.track_domain.Track
import com.mariomg.tagalong.track_interactors.LoadAllTracksForTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagTracksViewModel
@Inject
constructor(
    private val loadAllTracksForTag: LoadAllTracksForTag
) : TracksViewModel() {

    override var loading by mutableStateOf(false)

    override val tracks = mutableStateListOf<Track>()

    val tag = mutableStateOf<Tag?>(null)

    override var screenTitle by mutableStateOf("")

    override val dialogQueue = DialogQueue()

    fun onTriggerEvent(event: TagTracksEvent) {
        viewModelScope.launch {
            when (event) {
                is InitTagTracksEvent -> {
                    init(event.tag)
                }
                is LoadTagTracksEvent -> {
                    loadTracks()
                }
            }
        }
    }

    private fun init(tag: Tag) {
        this.tag.value = tag
        screenTitle = "#${tag.name}"
    }

    private fun loadTracks() {
        loadAllTracksForTag
            .execute(tag = this.tag.value!!)
            .on(
                loading = { loading = it },
                success = { tracks ->
                    this.tracks.clear()
                    this.tracks.addAll(tracks)
                },
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
    }

}