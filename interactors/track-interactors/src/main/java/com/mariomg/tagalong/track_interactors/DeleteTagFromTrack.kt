package com.mariomg.tagalong.track_interactors

import com.mariomg.tagalong.interactors_core.data.DataState
import com.mariomg.tagalong.interactors_core.data.DataState.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import com.mariomg.tagalong.tag_domain.Tag
import com.mariomg.tagalong.track_domain.Track
import com.mariomg.tagalong.track_interactors_core.repositories.TrackCacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTagFromTrack(
    private val trackCacheRepository: TrackCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(tag: Tag, track: Track): Flow<DataState<Unit>> = flow {
        try {
            emit(Loading(true))

            trackCacheRepository.deleteTagFromTrack(tag = tag, track = track)

            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}