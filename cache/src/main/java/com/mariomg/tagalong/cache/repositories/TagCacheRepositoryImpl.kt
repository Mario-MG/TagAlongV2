package com.mariomg.tagalong.cache.repositories

import com.mariomg.tagalong.cache.dao.TagDao
import com.mariomg.tagalong.cache.model.TagEntityMapper
import com.mariomg.tagalong.tag_domain.Tag
import com.mariomg.tagalong.tag_domain.TagInfo
import com.mariomg.tagalong.tag_interactors_core.repositories.TagCacheRepository
import com.mariomg.tagalong.track_domain.Track

class TagCacheRepositoryImpl(
    private val tagDao: TagDao,
    private val tagEntityMapper: TagEntityMapper
) : TagCacheRepository {

    override suspend fun getAll(): List<Tag> {
        return tagEntityMapper.mapToDomainModelList(tagDao.getAll())
    }

    override suspend fun getTagByName(tagName: String): Tag? {
        return tagDao.getByName(tagName)?.let(tagEntityMapper::mapToDomainModel)
    }

    override suspend fun getTagsForTrack(track: Track): List<Tag> {
        return tagEntityMapper.mapToDomainModelList(tagDao.getTagsForTrackById(track.uri)) // TODO: Somehow abstract the use of the URI
    }

    override suspend fun createNewTag(tagInfo: TagInfo): Tag {
        val newTagId = tagDao.insert(tagEntityMapper.mapFromDomainModel(tagInfo))
        return tagDao.getById(newTagId)?.let(tagEntityMapper::mapToDomainModel)
            ?: throw IllegalStateException()
    }

}