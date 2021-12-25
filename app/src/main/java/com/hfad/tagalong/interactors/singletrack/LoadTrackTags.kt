package com.hfad.tagalong.interactors.singletrack

import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadTrackTags(
    private val tagDao: TagDao,
    private val tagEntityMapper: TagEntityMapper
) {

    fun execute(track: Track): Flow<DataState<List<Tag>>> = flow {
        try {
            emit(DataState.Loading)

            val tagsForTrack = getTagsForTrack(track = track)

            emit(DataState.Success(tagsForTrack))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun getTagsForTrack(track: Track): List<Tag> {
        return tagEntityMapper.toDomainList(tagDao.getTagsForTrackById(track.uri)) // TODO: Make it more obvious that the URI is what must be used
    }

}