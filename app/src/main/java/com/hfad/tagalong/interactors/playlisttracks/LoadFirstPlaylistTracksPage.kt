package com.hfad.tagalong.interactors.playlisttracks

import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.model.TrackDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadFirstPlaylistTracksPage(
    private val trackService: RetrofitTrackService,
    private val trackDtoMapper: TrackDtoMapper
) {

    fun execute(
        auth: String,
        playlist: Playlist
    ): Flow<DataState<List<Track>>> = flow {
        try {
            emit(DataState.Loading)

            val tracks = getFirstTracksPageFromNetwork(
                auth = auth,
                playlist = playlist
            )

            emit(DataState.Success(tracks))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
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