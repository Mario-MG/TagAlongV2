package com.hfad.tagalong.interactors.playlisttracks

import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.model.TrackDtoMapper
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.track_domain.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadFirstPlaylistTracksPage(
    private val trackService: RetrofitTrackService,
    private val trackDtoMapper: TrackDtoMapper,
    private val networkErrorHandler: ErrorHandler
) {

    fun execute(
        auth: String,
        playlist: Playlist
    ): Flow<DataState<List<Track>>> = flow {
        try {
            emit(DataState.Loading(true))

            val tracks = getFirstTracksPageFromNetwork(
                auth = auth,
                playlist = playlist
            )

            emit(DataState.Success(tracks))
        } catch (e: Exception) {
            emit(DataState.Error(networkErrorHandler.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
        }
    }

    private suspend fun getFirstTracksPageFromNetwork(
        auth: String,
        playlist: Playlist
    ): List<Track> {
        val playlistItemsPage = trackService.getItemsInPlaylist(
            auth = auth,
            playlistId = playlist.id
        )
        val tracksNetworkModelList = playlistItemsPage.items.map { it.track }
        return trackDtoMapper.toDomainList(tracksNetworkModelList)
    }

}