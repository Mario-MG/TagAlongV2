package com.hfad.tagalong.presentation.ui.playlists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.interactors.data.on
import com.hfad.tagalong.interactors.playlists.LoadFirstPlaylistsPage
import com.hfad.tagalong.interactors.playlists.LoadNextPlaylistsPage
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
    private val loadFirstPlaylistsPage: LoadFirstPlaylistsPage,
    private val loadNextPlaylistsPage: LoadNextPlaylistsPage,
    private val sessionManager: SessionManager
) : BaseViewModel() {

    val playlists = mutableStateListOf<Playlist>()

    var loading by mutableStateOf(false)
        private set

    override val dialogQueue = DialogQueue()

    private var firstPageLoaded = false // TODO: Find out a way to improve this

    private var allPlaylistsLoaded = false // TODO: Find out a way to improve this

    fun onTriggerEvent(event: PlaylistsEvent) {
        viewModelScope.launch {
            when (event) {
                is FirstPageEvent -> {
                    if (!firstPageLoaded) {
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
        loadFirstPlaylistsPage
            .execute(auth = sessionManager.getAuthorizationHeader())
            .on(
                loading = ::loading::set,
                success = { playlists ->
                    this.playlists.clear()
                    this.playlists.addAll(playlists)
                    firstPageLoaded = true
                },
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
    }

    private fun loadNextPage() {
        val currentListSize = this.playlists.size
        loadNextPlaylistsPage
            .execute(
                auth = sessionManager.getAuthorizationHeader(),
                offset = currentListSize
            )
            .on(
                loading = ::loading::set,
                success = { playlists ->
                    if (playlists.isEmpty()) {
                        allPlaylistsLoaded = true
                    } else {
                        this.playlists.addAll(playlists)
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