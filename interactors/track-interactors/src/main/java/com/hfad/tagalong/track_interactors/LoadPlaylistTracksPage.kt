package com.hfad.tagalong.track_interactors

import com.hfad.tagalong.interactors_core.PAGE_SIZE
import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.track_domain.Track
import com.hfad.tagalong.track_interactors_core.repositories.TrackNetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadPlaylistTracksPage(
    private val trackNetworkRepository: TrackNetworkRepository,
    private val networkErrorMapper: ErrorMapper
) {

    fun execute(
        playlist: Playlist,
        page: Int = 0
    ): Flow<DataState<List<Track>>> = flow {
        try {
            emit(Loading(true))

            val tracks = trackNetworkRepository.getTracksInPlaylist(
                playlist = playlist,
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