package com.hfad.tagalong.interactors.playlists

import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadNextPlaylistsPage(
    private val playlistService: RetrofitPlaylistService,
    private val playlistDtoMapper: PlaylistDtoMapper
) {

    fun execute(
        auth: String,
        offset: Int
    ): Flow<DataState<List<Playlist>>> = flow {
        try {
            emit(DataState.Loading)

            val playlists = getNextPlaylistsPageFromNetwork(auth, offset)

            emit(DataState.Success(playlists))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun getNextPlaylistsPageFromNetwork(
        auth: String,
        offset: Int
    ): List<Playlist> {
        return playlistDtoMapper.toDomainList(
            playlistService.getList(auth = auth, offset = offset).items
        )
    }

}