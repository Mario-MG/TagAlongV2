package com.hfad.tagalong.interactors.singletrack

import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class CreateTag(
    private val tagDao: TagDao,
    private val tagEntityMapper: TagEntityMapper
) {

    fun execute(tagName: String): Flow<DataState<Tag>> = flow {
        try {
            emit(DataState.Loading)

            val allTags = getAllTags()

            val existingTag = allTags.find { tag -> tag.name == tagName }
            if (existingTag != null) {
                emit(DataState.Success(existingTag))
            } else {
                val tag = createNewTag(tagName = tagName)
                emit(DataState.Success(tag))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun getAllTags(): List<Tag> {
        return tagEntityMapper.toDomainList(tagDao.getAll())
    }

    private suspend fun createNewTag(tagName: String): Tag {
        val newTagId = tagDao.insert(tagEntityMapper.mapFromDomainModel(Tag(name = tagName)))
        return tagEntityMapper.mapToDomainModel(tagDao.getById(newTagId))
    }

}