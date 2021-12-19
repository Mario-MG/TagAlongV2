package com.hfad.tagalong.interactors.tagtracks

import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class LoadAllTagTracks(
    private val trackDao: TrackDao,
    private val trackEntityMapper: TrackEntityMapper
) {

    fun execute(tag: Tag): Flow<DataState<List<Track>>> = flow {
        try {
            emit(DataState.Loading)

            val tracks = getAllTracksForTag(tag)

            emit(DataState.Success(tracks))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun getAllTracksForTag(tag: Tag): List<Track> {
        return trackEntityMapper.toDomainList(trackDao.getTracksWithAnyOfTheTagsById(tag.id))
    }

}