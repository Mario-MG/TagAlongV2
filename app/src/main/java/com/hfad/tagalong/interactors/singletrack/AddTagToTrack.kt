package com.hfad.tagalong.interactors.singletrack

import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.dao.TrackTagCrossRefDao
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.cache.model.TrackTagCrossRef
import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddTagToTrack(
    private val trackDao: TrackDao,
    private val trackEntityMapper: TrackEntityMapper,
    private val trackTagCrossRefDao: TrackTagCrossRefDao
) {

    fun execute(tag: Tag, track: Track): Flow<DataState<Unit>> = flow {
        try {
            emit(DataState.Loading)

            saveTrack(track = track)
            addTagToTrack(tag = tag, track = track)

            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun saveTrack(track: Track) { // TODO: This will be removed when cache is implemented
        trackDao.insert(trackEntityMapper.mapFromDomainModel(track))
    }

    private suspend fun addTagToTrack(tag: Tag, track: Track) {
        trackTagCrossRefDao.insert(
            TrackTagCrossRef(
                trackId = track.uri, // TODO: Make it more obvious that the URI is what must be used
                tagId = tag.id
            ) // TODO: How to abstract this?
        )
    }

}