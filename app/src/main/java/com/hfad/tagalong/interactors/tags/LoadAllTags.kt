package com.hfad.tagalong.interactors.tags

import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.tag_domain.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadAllTags( // TODO: Interactor shared between several viewmodels??
    private val tagDao: TagDao,
    private val tagEntityMapper: TagEntityMapper,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(): Flow<DataState<List<Tag>>> = flow {
        try {
            emit(DataState.Loading(true))

            val tags = getAllTags()

            emit(DataState.Success(tags))
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
        }
    }

    private suspend fun getAllTags(): List<Tag> {
        return tagEntityMapper.toDomainList(tagDao.getAll())
    }

}