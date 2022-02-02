package com.hfad.tagalong.tag_interactors

import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.tag_domain.TagInfo
import com.hfad.tagalong.tag_interactors_core.repositories.TagCacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FindOrCreateTag(
    private val tagCacheRepository: TagCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(tagInfo: TagInfo): Flow<DataState<Tag>> = flow {
        try {
            emit(Loading(true))

            tagCacheRepository.getTagByName(tagInfo.name)?.let { existingTag ->
                emit(Success(existingTag))
            } ?: run {
                val newTag = tagCacheRepository.createNewTag(tagInfo)
                emit(Success(newTag))
            }
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}