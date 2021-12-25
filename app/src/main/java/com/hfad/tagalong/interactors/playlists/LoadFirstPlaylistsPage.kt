package com.hfad.tagalong.interactors.playlists

import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadFirstPlaylistsPage(
    private val playlistService: RetrofitPlaylistService,
    private val playlistDtoMapper: PlaylistDtoMapper
) {

    fun execute(auth: String): Flow<DataState<List<Playlist>>> = flow {
        try {
            emit(DataState.Loading)

            val playlists = getFirstPlaylistsPageFromNetwork(auth)

            emit(DataState.Success(playlists))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun getFirstPlaylistsPageFromNetwork(auth: String): List<Playlist> {
        return playlistDtoMapper.toDomainList(playlistService.getList(auth = auth).items)
    }

}