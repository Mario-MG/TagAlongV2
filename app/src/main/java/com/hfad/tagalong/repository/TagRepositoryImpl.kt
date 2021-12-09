package com.hfad.tagalong.repository

import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track

class TagRepositoryImpl(
    private val tagDao: TagDao,
    private val tagEntityMapper: TagEntityMapper
) : TagRepository {

    override suspend fun getAll(): List<Tag> {
        return tagEntityMapper.toDomainList(tagDao.getAll())
    }

    override suspend fun getAllForTrack(track: Track): List<Tag> {
        return tagEntityMapper.toDomainList(tagDao.getTagsForTrackById(track.uri)) // TODO
    }

}