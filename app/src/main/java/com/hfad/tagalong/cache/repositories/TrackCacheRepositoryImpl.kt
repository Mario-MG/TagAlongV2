package com.hfad.tagalong.cache.repositories

import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.track_domain.Track
import com.hfad.tagalong.track_interactors_core.repositories.TrackCacheRepository

class TrackCacheRepositoryImpl(
    private val trackDao: TrackDao,
    private val trackEntityMapper: TrackEntityMapper
) : TrackCacheRepository {

    override suspend fun getAllTracksForTag(tag: Tag): List<Track> {
        return trackEntityMapper.toDomainList(trackDao.getTracksWithAnyOfTheTagsById(tag.id))
    }

}