package com.mariomg.tagalong.tag_interactors

import com.mariomg.tagalong.interactors_core.data.DataState
import com.mariomg.tagalong.interactors_core.data.DataState.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import com.mariomg.tagalong.tag_domain.Tag
import com.mariomg.tagalong.tag_interactors_core.repositories.TagCacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadAllTags(
    private val tagCacheRepository: TagCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(): Flow<DataState<List<Tag>>> = flow {
        try {
            emit(Loading(true))

            val tags = tagCacheRepository.getAll()

            emit(Success(tags))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}