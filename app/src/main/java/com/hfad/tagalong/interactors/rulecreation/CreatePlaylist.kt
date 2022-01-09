package com.hfad.tagalong.interactors.rulecreation

import com.google.gson.JsonObject
import com.hfad.tagalong.cache.dao.PlaylistDao
import com.hfad.tagalong.cache.model.PlaylistEntityMapper
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreatePlaylist(
    private val playlistService: RetrofitPlaylistService,
    private val playlistDtoMapper: PlaylistDtoMapper,
    private val networkErrorHandler: ErrorHandler,
    private val playlistDao: PlaylistDao,
    private val playlistEntityMapper: PlaylistEntityMapper,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(
        auth: String,
        userId: String,
        playlistName: String
    ): Flow<DataState<Playlist>> = flow {
        try {
            emit(DataState.Loading(true))

            val playlist = createNewPlaylist(
                auth = auth,
                userId = userId,
                playlistName = playlistName
            )

            try {
                cachePlaylist(playlist)

                emit(DataState.Success(playlist))
            } catch (e: Exception) {
                emit(DataState.Error(cacheErrorHandler.parseError(e)))
            }

        } catch (e: Exception) {
            emit(DataState.Error(networkErrorHandler.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
        }
    }

    // TODO: When cache is implemented, split this function into three/four:
    //  1) Create playlist using service
    //  1.5) Retrieve playlist from service??
    //  2) Insert playlist into cache
    //  3) Retrieve playlist from cache
    private suspend fun createNewPlaylist(
        auth: String,
        userId: String,
        playlistName: String
    ): Playlist {
        return playlistDtoMapper.mapToDomainModel(
            playlistService.create(
                auth = auth,
                userId = userId,
                body = JsonObject().apply { addProperty("name", playlistName) }
            )
        )
    }

    private suspend fun cachePlaylist(playlist: Playlist) {
        playlistDao.insert(playlistEntityMapper.mapFromDomainModel(playlist))
    }

}