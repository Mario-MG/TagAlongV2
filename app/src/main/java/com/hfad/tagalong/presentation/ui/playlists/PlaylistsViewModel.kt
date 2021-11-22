package com.hfad.tagalong.presentation.ui.playlists

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.BuildConfig
import com.hfad.tagalong.Session
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.presentation.ui.playlists.PlaylistsEvent.FirstPageEvent
import com.hfad.tagalong.presentation.ui.playlists.PlaylistsEvent.NextPageEvent
import com.hfad.tagalong.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel
@Inject
constructor(
    private val playlistRepository: PlaylistRepository,
    private val session: Session
) : ViewModel() {

    val playlists = mutableStateListOf<Playlist>()

    val loading = mutableStateOf(false)

    private var allPlaylistsLoaded = false // TODO: Find out a way to improve this

    init {
        onTriggerEvent(FirstPageEvent)
    }

    fun onTriggerEvent(event: PlaylistsEvent) {
        viewModelScope.launch {
            when (event) {
                is FirstPageEvent -> {
                    loadFirstPage()
                }
                is NextPageEvent -> {
                    loadNextPage()
                }
            }
        }
    }

    private suspend fun loadFirstPage() {
        loading.value = true
        val playlists = playlistRepository.getList(token = session.getAuthorization())
        this.playlists.clear()
        this.playlists.addAll(playlists)
        loading.value = false
    }

    private suspend fun loadNextPage() {
        if (!allPlaylistsLoaded) {
            loading.value = true
            val currentListSize = this.playlists.size
            val newPlaylists = playlistRepository.getList(
                token = session.getAuthorization(),
                offset = currentListSize
            )
            if (BuildConfig.DEBUG) delay(1000) // TODO: Remove
            if (newPlaylists.isEmpty()) {
                allPlaylistsLoaded = true
            } else {
                this.playlists.addAll(newPlaylists)
            }
            loading.value = false
        }
    }

}