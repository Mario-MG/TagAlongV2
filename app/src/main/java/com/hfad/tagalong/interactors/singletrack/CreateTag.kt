package com.hfad.tagalong.interactors.singletrack

import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateTag(
    private val tagDao: TagDao,
    private val tagEntityMapper: TagEntityMapper,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(tagName: String): Flow<DataState<Tag>> = flow {
        try {
            emit(DataState.Loading(true))

            val allTags = getAllTags() // TODO: Create function to find by name

            val existingTag = allTags.find { tag -> tag.name == tagName }
            if (existingTag != null) {
                emit(DataState.Success(existingTag))
            } else {
                val tag = createNewTag(tagName = tagName)
                emit(DataState.Success(tag))
            }
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
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