package com.mariomg.tagalong.playlist_interactors

import com.mariomg.tagalong.interactors_core.data.DataState
import com.mariomg.tagalong.interactors_core.data.DataState.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import com.mariomg.tagalong.playlist_domain.Playlist
import com.mariomg.tagalong.playlist_domain.PlaylistInfo
import com.mariomg.tagalong.playlist_interactors_core.repositories.PlaylistCacheRepository
import com.mariomg.tagalong.playlist_interactors_core.repositories.PlaylistNetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreatePlaylist(
    private val playlistNetworkRepository: PlaylistNetworkRepository,
    private val networkErrorMapper: ErrorMapper,
    private val playlistCacheRepository: PlaylistCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(playlistInfo: PlaylistInfo): Flow<DataState<Playlist>> = flow {
        try {
            emit(Loading(true))

            val playlist = playlistNetworkRepository.create(playlistInfo = playlistInfo)

            try {
                playlistCacheRepository.create(playlist)

                emit(Success(playlist))
            } catch (e: Exception) {
                // TODO: Rollback playlist creation
                emit(Error(cacheErrorMapper.parseError(e)))
            }

        } catch (e: Exception) {
            emit(Error(networkErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}