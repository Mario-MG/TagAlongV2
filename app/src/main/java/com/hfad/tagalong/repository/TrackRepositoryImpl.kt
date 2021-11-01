package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.model.TrackDtoMapper

class TrackRepositoryImpl(
    private val trackService: RetrofitTrackService,
    private val trackMapper: TrackDtoMapper
): TrackRepository {

    override suspend fun getItemsInPlaylist(token: String, playlistId: String, limit: Int, offset: Int): List<Track> {
        val playlistItemsPage = trackService.getItemsInPlaylist(
            token = token,
            playlistId = playlistId,
            limit = limit,
            offset = offset
        )
        val tracksNetworkModelList = playlistItemsPage.items.map { it.track }
        return trackMapper.toDomainList(tracksNetworkModelList)
    }

    override suspend fun getTrack(token: String, trackId: String): Track {
        return trackMapper.mapToDomainModel(trackService.getTrack(token = token, trackId = trackId))
    }

}