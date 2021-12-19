package com.hfad.tagalong.interactors.tags

import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadAllTags(
    private val tagDao: TagDao,
    private val tagEntityMapper: TagEntityMapper
) {

    fun execute(): Flow<DataState<List<Tag>>> = flow {
        try {
            emit(DataState.Loading)

            val tags = getAllTags()

            emit(DataState.Success(tags))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun getAllTags(): List<Tag> {
        return tagEntityMapper.toDomainList(tagDao.getAll())
    }

}