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

    val playlistList: MutableState<List<PlaylistDto>> = mutableStateOf(listOf()) // TODO: This is for testing purposes only

    init { // TODO: This is for testing purposes only
        viewModelScope.launch {
            getListOfPlaylists()
        }
    }

    suspend fun getListOfPlaylists() { // TODO: This is for testing purposes only
        val playlist = playlistRepository.getList(token = session.token?: "Bearer fakeToken") // TODO: Handle null token
        this.playlistList.value = playlist.items
    }

}