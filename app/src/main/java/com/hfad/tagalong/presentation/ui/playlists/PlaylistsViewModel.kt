package com.hfad.tagalong.presentation.ui.playlists

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.interactors.playlists.LoadFirstPlaylistsPage
import com.hfad.tagalong.interactors.playlists.LoadNextPlaylistsPage
import com.hfad.tagalong.presentation.ui.playlists.PlaylistsEvent.FirstPageEvent
import com.hfad.tagalong.presentation.ui.playlists.PlaylistsEvent.NextPageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel
@Inject
constructor(
    private val loadFirstPlaylistsPage: LoadFirstPlaylistsPage,
    private val loadNextPlaylistsPage: LoadNextPlaylistsPage,
    private val sessionManager: SessionManager // TODO: Think about moving this dependency down to the use cases
) : ViewModel() {

    val playlists = mutableStateListOf<Playlist>()

    val loading = mutableStateOf(false)

    private var allPlaylistsLoaded = false // TODO: Find out a way to improve this

    fun onTriggerEvent(event: PlaylistsEvent) {
        viewModelScope.launch {
            when (event) {
                is FirstPageEvent -> {
                    loadFirstPage()
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
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { playlists ->
                    this.playlists.clear()
                    this.playlists.addAll(playlists)
                }

                dataState.error?.let { error ->
                    // TODO
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadNextPage() {
        val currentListSize = this.playlists.size
        loadNextPlaylistsPage
            .execute(
                auth = sessionManager.getAuthorizationHeader(),
                offset = currentListSize
            )
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { playlists ->
                    if (playlists.isEmpty()) {
                        allPlaylistsLoaded = true
                    } else {
                        this.playlists.addAll(playlists)
                    }
                }

                dataState.error?.let { error ->
                    // TODO
                }
            }
            .launchIn(viewModelScope)
    }

}