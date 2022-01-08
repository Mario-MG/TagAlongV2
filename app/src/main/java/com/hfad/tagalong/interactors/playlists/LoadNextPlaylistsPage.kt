package com.hfad.tagalong.interactors.playlists

import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadNextPlaylistsPage(
    private val playlistService: RetrofitPlaylistService,
    private val playlistDtoMapper: PlaylistDtoMapper,
    private val networkErrorHandler: ErrorHandler
) {

    fun execute(
        auth: String,
        offset: Int
    ): Flow<DataState<List<Playlist>>> = flow {
        try {
            emit(DataState.Loading(true))

            val playlists = getNextPlaylistsPageFromNetwork(auth, offset)

            emit(DataState.Success(playlists))
        } catch (e: Exception) {
            emit(DataState.Error(networkErrorHandler.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
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