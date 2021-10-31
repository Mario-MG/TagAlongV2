package com.hfad.tagalong.presentation.ui.playlists

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.Session
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel
@Inject
constructor(
    private val playlistRepository: PlaylistRepository,
    private val session: Session
) : ViewModel() {

    val playlistList = mutableStateListOf<Playlist>()

    init {
        viewModelScope.launch {
            getListOfPlaylists()
        }
    }

    private suspend fun getListOfPlaylists() {
        val playlists = playlistRepository.getList(token = session.getToken())
        this.playlistList.clear()
        this.playlistList.addAll(playlists)
    }

}