package com.hfad.tagalong.repository

import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.dao.TrackTagCrossRefDao
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.cache.model.TrackTagCrossRef
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track

class TrackTagRepositoryImpl(
    private val tagDao: TagDao,
    private val trackDao: TrackDao,
    private val trackTagCrossRefDao: TrackTagCrossRefDao,
    private val tagEntityMapper: TagEntityMapper,
    private val trackEntityMapper: TrackEntityMapper
) : TrackTagRepository {

    override suspend fun addTagToTrack(tag: Tag, track: Track) {
        val newTagId = tagDao.insert(tagEntityMapper.mapFromDomainModel(tag))
        val isExistingTag = newTagId == -1L
        trackDao.insert(trackEntityMapper.mapFromDomainModel(track))
        trackTagCrossRefDao.insert(
            TrackTagCrossRef(
                trackId = track.uri,
                tagId = if (isExistingTag) tag.id else newTagId
            ) // TODO: How to abstract this?
        )
    }

    override suspend fun deleteTagFromTrack(tag: Tag, track: Track) {
        trackTagCrossRefDao.delete(
            TrackTagCrossRef(
                trackId = track.uri,
                tagId = tag.id
            ) // TODO: How to abstract this?
        )
    }

}