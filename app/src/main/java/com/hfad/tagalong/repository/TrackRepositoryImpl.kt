package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.model.TrackDtoMapper

class TrackRepositoryImpl(
    private val trackService: RetrofitTrackService,
    private val trackMapper: TrackDtoMapper
): TrackRepository {

    override suspend fun getItemsInPlaylist(token: String, playlistId: String, limit: Int, offset: Int): List<Track> {
        return trackMapper.toDomainList(trackService.getItemsInPlaylist(token = token, playlistId = playlistId, limit = limit, offset = offset).items.map { it.track })
    }

}