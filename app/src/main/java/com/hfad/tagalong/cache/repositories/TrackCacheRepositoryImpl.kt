package com.hfad.tagalong.cache.repositories

import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.dao.TrackTagCrossRefDao
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.cache.model.TrackTagCrossRefMapper
import com.hfad.tagalong.rule_domain.Rule
import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.track_domain.Track
import com.hfad.tagalong.track_interactors_core.repositories.TrackCacheRepository

class TrackCacheRepositoryImpl(
    private val trackDao: TrackDao,
    private val trackEntityMapper: TrackEntityMapper,
    private val trackTagCrossRefDao: TrackTagCrossRefDao,
    private val trackTagCrossRefMapper: TrackTagCrossRefMapper
) : TrackCacheRepository {

    override suspend fun getAllTracksForTag(tag: Tag): List<Track> {
        return trackEntityMapper.toDomainList(trackDao.getTracksWithAnyOfTheTagsById(tag.id))
    }

    override suspend fun getAllTracksForRule(rule: Rule): List<Track> {
        val tagIds = rule.tags.map(Tag::id).toLongArray()
        return trackEntityMapper.toDomainList(
            if (rule.optionality)
                trackDao.getTracksWithAnyOfTheTagsById(*tagIds)
            else
                trackDao.getTracksWithAllOfTheTagsById(*tagIds)
        )
    }

    override suspend fun saveTrack(track: Track) {
        trackDao.insert(trackEntityMapper.mapFromDomainModel(track))
    }

    override suspend fun addTagToTrack(tag: Tag, track: Track) {
        trackTagCrossRefDao.insert(
            trackTagCrossRefMapper.mapFromDomainModel(track = track, tag = tag)
        )
    }

    override suspend fun deleteTagFromTrack(tag: Tag, track: Track) {
        trackTagCrossRefDao.delete(
            trackTagCrossRefMapper.mapFromDomainModel(track = track, tag = tag)
        )
    }

}