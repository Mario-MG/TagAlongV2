package com.hfad.tagalong

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.network.models.PlaylistDto
import com.hfad.tagalong.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val playlistRepository: PlaylistRepository,
    private val session: Session
) : ViewModel() {

    val playlist: MutableState<PlaylistDto?> = mutableStateOf(null) // TODO: This is for testing purposes only

    init { // TODO: This is for testing purposes only
        viewModelScope.launch {
            getPlaylist("7gYY3wjQSPucjaJlI7DMJB")
        }
    }

    suspend fun getPlaylist(id: String) { // TODO: This is for testing purposes only
        val playlist = playlistRepository.get(token = session.token?: "Bearer fakeToken", id = id) // TODO: Handle null token
        this.playlist.value = playlist
    }

}