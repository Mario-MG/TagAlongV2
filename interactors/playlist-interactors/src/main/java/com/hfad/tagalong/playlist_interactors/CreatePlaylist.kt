package com.hfad.tagalong.playlist_interactors

import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.Error
import com.hfad.tagalong.interactors_core.data.DataState.Loading
import com.hfad.tagalong.interactors_core.data.DataState.Success
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.playlist_domain.PlaylistInfo
import com.hfad.tagalong.playlist_interactors_core.repositories.PlaylistCacheRepository
import com.hfad.tagalong.playlist_interactors_core.repositories.PlaylistNetworkRepository
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