package com.hfad.tagalong.presentation.ui.playlists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.interactors_core.util.on
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.playlist_interactors.LoadPlaylistsPage
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.presentation.ui.BaseViewModel
import com.hfad.tagalong.presentation.ui.playlists.PlaylistsEvent.FirstPageEvent
import com.hfad.tagalong.presentation.ui.playlists.PlaylistsEvent.NextPageEvent
import com.hfad.tagalong.presentation.util.DialogQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel
@Inject
constructor(
    private val loadPlaylistsPage: LoadPlaylistsPage
) : BaseViewModel() {

    val playlists = mutableStateListOf<Playlist>()

    var loading by mutableStateOf(false)
        private set

    override val dialogQueue = DialogQueue()

    private var nextPage = 0

    private var allPlaylistsLoaded = false // TODO: Find out a way to improve this

    fun onTriggerEvent(event: PlaylistsEvent) {
        viewModelScope.launch {
            when (event) {
                is FirstPageEvent -> {
                    if (nextPage == 0) {
                        loadFirstPage()
                    }
                }
                is NextPageEvent -> {
                    if (!allPlaylistsLoaded) {
                        loadNextPage()
                    }
                }
            }
        }
    }

    private fun loadFirstPage() {
        loadPlaylistsPage
            .execute()
            .on(
                loading = ::loading::set,
                success = { playlists ->
                    this.playlists.clear()
                    this.playlists.addAll(playlists)
                    nextPage = 1
                },
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
    }

    private fun loadNextPage() {
        loadPlaylistsPage
            .execute(page = nextPage)
            .on(
                loading = ::loading::set,
                success = { playlists ->
                    if (playlists.isEmpty()) {
                        allPlaylistsLoaded = true
                    } else {
                        this.playlists.addAll(playlists)
                        nextPage += 1 // TODO: Make sure there are no synchronization issues
                    }
                },
                error = { error ->
                    allPlaylistsLoaded = true // TODO: Improve this (its only purpose is to avoid the event being triggered in an infinite loop)
                    appendGenericErrorToQueue(error)
                }
            )
            .launchIn(viewModelScope)
    }

}