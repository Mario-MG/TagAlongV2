package com.hfad.tagalong.presentation.ui.tagtracks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.interactors.data.on
import com.hfad.tagalong.interactors.tagtracks.LoadAllTagTracks
import com.hfad.tagalong.presentation.ui.tagtracks.TagTracksEvent.InitTagTracksEvent
import com.hfad.tagalong.presentation.ui.tagtracks.TagTracksEvent.LoadTagTracksEvent
import com.hfad.tagalong.presentation.ui.tracks.TracksViewModel
import com.hfad.tagalong.presentation.util.DialogQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagTracksViewModel
@Inject
constructor(
    private val loadAllTagTracks: LoadAllTagTracks
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
        loadAllTagTracks
            .execute(tag = this.tag.value!!)
            .on(
                loadingStateChange = { loading = it },
                success = { tracks ->
                    this.tracks.clear()
                    this.tracks.addAll(tracks)
                },
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
    }

}