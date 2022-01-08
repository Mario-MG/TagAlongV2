package com.hfad.tagalong.interactors.tagtracks

import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.interactors.data.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class LoadAllTagTracks(
    private val trackDao: TrackDao,
    private val trackEntityMapper: TrackEntityMapper,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(tag: Tag): Flow<DataState<List<Track>>> = flow {
        try {
            emit(DataState.Loading(true))

            val tracks = getAllTracksForTag(tag)

            emit(DataState.Success(tracks))
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
        }
    }

    private suspend fun getAllTracksForTag(tag: Tag): List<Track> {
        return trackEntityMapper.toDomainList(trackDao.getTracksWithAnyOfTheTagsById(tag.id))
    }

}