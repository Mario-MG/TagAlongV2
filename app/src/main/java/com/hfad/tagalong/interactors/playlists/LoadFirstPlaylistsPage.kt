package com.hfad.tagalong.interactors.playlists

import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import com.hfad.tagalong.playlist_domain.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadFirstPlaylistsPage(
    private val playlistService: RetrofitPlaylistService,
    private val playlistDtoMapper: PlaylistDtoMapper,
    private val networkErrorHandler: ErrorHandler
) {

    fun execute(auth: String): Flow<DataState<List<Playlist>>> = flow {
        try {
            emit(DataState.Loading(true))

            val playlists = getFirstPlaylistsPageFromNetwork(auth)

            emit(DataState.Success(playlists))
        } catch (e: Exception) {
            emit(DataState.Error(networkErrorHandler.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
        }
    }

    private suspend fun getFirstPlaylistsPageFromNetwork(auth: String): List<Playlist> {
        return playlistDtoMapper.toDomainList(playlistService.getList(auth = auth).items)
    }

}