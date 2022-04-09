package com.hfad.tagalong.track_interactors

import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.rule_domain.Rule
import com.hfad.tagalong.track_domain.Track
import com.hfad.tagalong.track_interactors_core.repositories.TrackCacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadTracksForRule(
    private val trackCacheRepository: TrackCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(rule: Rule): Flow<DataState<List<Track>>> = flow {
        try {
            emit(Loading(true))

            val tracks = trackCacheRepository.getAllTracksForRule(rule)

            emit(Success(tracks))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}