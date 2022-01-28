package com.hfad.tagalong.cache.repositories

import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.tag_interactors_core.repositories.TagCacheRepository
import com.hfad.tagalong.track_domain.Track

class TagCacheRepositoryImpl(
    private val tagDao: TagDao,
    private val tagEntityMapper: TagEntityMapper
) : TagCacheRepository {

    override suspend fun getAll(): List<Tag> {
        return tagEntityMapper.toDomainList(tagDao.getAll())
    }

    override suspend fun getTagByName(tagName: String): Tag? {
        return tagDao.getByName(tagName)?.let(tagEntityMapper::mapToDomainModel)
    }

    override suspend fun getTagsForTrack(track: Track): List<Tag> {
        return tagEntityMapper.toDomainList(tagDao.getTagsForTrackById(track.uri)) // TODO: Somehow abstract the use of the URI
    }

    override suspend fun createNewTag(tagName: String): Tag {
        val newTagId = tagDao.insert(tagEntityMapper.mapFromDomainModel(Tag(id = 0, name = tagName, size = 0))) // TODO: Refactor to take TagInfo without id
        return tagDao.getById(newTagId)?.let(tagEntityMapper::mapToDomainModel)
            ?: throw IllegalStateException()
    }

}