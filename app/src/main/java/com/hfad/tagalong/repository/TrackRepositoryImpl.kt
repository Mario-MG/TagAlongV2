package com.hfad.tagalong.repository

import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.model.TrackDtoMapper

class TrackRepositoryImpl(
    private val trackService: RetrofitTrackService,
    private val trackDtoMapper: TrackDtoMapper,
    private val trackDao: TrackDao,
    private val trackEntityMapper: TrackEntityMapper
): TrackRepository {

    override suspend fun getItemsInPlaylist(auth: String, playlistId: String, limit: Int, offset: Int): List<Track> {
        val playlistItemsPage = trackService.getItemsInPlaylist(
            auth = auth,
            playlistId = playlistId,
            limit = limit,
            offset = offset
        )
        val tracksNetworkModelList = playlistItemsPage.items.map { it.track }
        return trackDtoMapper.toDomainList(tracksNetworkModelList)
    }

    override suspend fun getTrack(auth: String, trackId: String): Track {
        return trackDtoMapper.mapToDomainModel(trackService.getTrack(auth = auth, trackId = trackId))
    }

    override suspend fun getTracksForTag(tagId: Long): List<Track> {
        return trackEntityMapper.toDomainList(trackDao.getTracksWithAnyOfTheTagsById(tagId))
    }

    override suspend fun getTracksWithAnyOfTheTags(tags: List<Tag>): List<Track> {
        return trackEntityMapper.toDomainList(trackDao.getTracksWithAnyOfTheTagsById(*tags.map(Tag::id).toLongArray()))
    }

    override suspend fun getTracksWithAllOfTheTags(tags: List<Tag>): List<Track> {
        return trackEntityMapper.toDomainList(trackDao.getTracksWithAllOfTheTagsById(*tags.map { it.id }.toLongArray()))
    }

}