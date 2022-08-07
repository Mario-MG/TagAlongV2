package com.mariomg.tagalong.track_interactors

import com.mariomg.tagalong.interactors_core.data.DataState
import com.mariomg.tagalong.interactors_core.data.DataState.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import com.mariomg.tagalong.tag_domain.Tag
import com.mariomg.tagalong.track_domain.Track
import com.mariomg.tagalong.track_interactors_core.repositories.TrackCacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadAllTracksForTag(
    private val trackCacheRepository: TrackCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(tag: Tag): Flow<DataState<List<Track>>> = flow {
        try {
            emit(Loading(true))

            val tracks = trackCacheRepository.getAllTracksForTag(tag)

            emit(Success(tracks))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}