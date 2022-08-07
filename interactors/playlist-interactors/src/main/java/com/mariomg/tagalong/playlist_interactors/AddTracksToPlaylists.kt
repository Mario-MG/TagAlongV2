package com.mariomg.tagalong.playlist_interactors

import com.mariomg.tagalong.interactors_core.data.DataState
import com.mariomg.tagalong.interactors_core.data.DataState.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import com.mariomg.tagalong.playlist_domain.Playlist
import com.mariomg.tagalong.playlist_interactors_core.repositories.PlaylistNetworkRepository
import com.mariomg.tagalong.track_domain.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddTracksToPlaylists(
    private val playlistNetworkRepository: PlaylistNetworkRepository,
    private val networkErrorMapper: ErrorMapper
) {

    fun execute(
        tracks: List<Track>,
        playlists: List<Playlist>
    ): Flow<DataState<Unit>> = flow {
        try {
            emit(Loading(true))

            for (playlist in playlists) {
                playlistNetworkRepository.addTracksToPlaylist(
                    tracks = tracks,
                    playlist = playlist
                )
            }

            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(networkErrorMapper.parseError(e)))

        } finally {
            emit(Loading(false))
        }
    }

}