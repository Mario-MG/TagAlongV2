package com.hfad.tagalong.tag_interactors

import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.tag_interactors_core.repositories.TagCacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FindOrCreateTag(
    private val tagCacheRepository: TagCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(tagName: String): Flow<DataState<Tag>> = flow {
        try {
            emit(Loading(true))

            tagCacheRepository.getTagByName(tagName)?.let { existingTag ->
                emit(Success(existingTag))
            } ?: run {
                val newTag = tagCacheRepository.createNewTag(tagName)
                emit(Success(newTag))
            }
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}