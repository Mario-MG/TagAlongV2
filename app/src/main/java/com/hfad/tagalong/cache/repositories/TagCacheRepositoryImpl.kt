package com.hfad.tagalong.cache.repositories

import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.tag_interactors_core.repositories.TagCacheRepository

class TagCacheRepositoryImpl(
    private val tagDao: TagDao,
    private val tagEntityMapper: TagEntityMapper
) : TagCacheRepository {

    override suspend fun getAll(): List<Tag> {
        return tagEntityMapper.toDomainList(tagDao.getAll())
    }

}