package com.hfad.tagalong.tag_interactors

import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.tag_interactors_core.repositories.TagCacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadAllTags(
    private val tagCacheRepository: TagCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(): Flow<DataState<List<Tag>>> = flow {
        try {
            emit(DataState.Loading(true))

            val tags = tagCacheRepository.getAll()

            emit(DataState.Success(tags))
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
        }
    }

}