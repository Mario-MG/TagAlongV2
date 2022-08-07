package com.mariomg.tagalong.playlist_interactors

import com.mariomg.tagalong.interactors_core.PAGE_SIZE
import com.mariomg.tagalong.interactors_core.data.DataState
import com.mariomg.tagalong.interactors_core.data.DataState.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import com.mariomg.tagalong.playlist_domain.Playlist
import com.mariomg.tagalong.playlist_interactors_core.repositories.PlaylistNetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadPlaylistsPage(
    private val playlistNetworkRepository: PlaylistNetworkRepository,
    private val networkErrorMapper: ErrorMapper
) {

    fun execute(page: Int = 0): Flow<DataState<List<Playlist>>> = flow {
        try {
            emit(Loading(true))

            val tracks = playlistNetworkRepository.getPlaylists(
                offset = page * PAGE_SIZE,
                limit = PAGE_SIZE
            )

            emit(Success(tracks))
        } catch (e: Exception) {
            emit(Error(networkErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}