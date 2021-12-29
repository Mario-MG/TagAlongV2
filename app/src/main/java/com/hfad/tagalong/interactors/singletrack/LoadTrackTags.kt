package com.hfad.tagalong.interactors.singletrack

import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.interactors.data.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadTrackTags(
    private val tagDao: TagDao,
    private val tagEntityMapper: TagEntityMapper,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(track: Track): Flow<DataState<List<Tag>>> = flow {
        try {
            emit(DataState.Loading)

            val tagsForTrack = getTagsForTrack(track = track)

            emit(DataState.Success(tagsForTrack))
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        }
    }

    private suspend fun getTagsForTrack(track: Track): List<Tag> {
        return tagEntityMapper.toDomainList(tagDao.getTagsForTrackById(track.uri)) // TODO: Make it more obvious that the URI is what must be used
    }

}