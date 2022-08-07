package com.mariomg.tagalong.tag_interactors

import com.mariomg.tagalong.interactors_core.data.DataState
import com.mariomg.tagalong.interactors_core.data.DataState.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import com.mariomg.tagalong.tag_domain.Tag
import com.mariomg.tagalong.tag_interactors_core.repositories.TagCacheRepository
import com.mariomg.tagalong.track_domain.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadTagsForTrack(
    private val tagCacheRepository: TagCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(track: Track): Flow<DataState<List<Tag>>> = flow {
        try {
            emit(Loading(true))

            val tagsForTrack = tagCacheRepository.getTagsForTrack(track)

            emit(Success(tagsForTrack))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}