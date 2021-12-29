package com.hfad.tagalong.interactors.singletrack

import com.hfad.tagalong.cache.dao.TrackTagCrossRefDao
import com.hfad.tagalong.cache.model.TrackTagCrossRef
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.interactors.data.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTagFromTrack(
    private val trackTagCrossRefDao: TrackTagCrossRefDao,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(tag: Tag, track: Track): Flow<DataState<Unit>> = flow {
        try {
            emit(DataState.Loading)

            deleteTagFromTrack(tag = tag, track = track)

            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        }
    }

    private suspend fun deleteTagFromTrack(tag: Tag, track: Track) {
        trackTagCrossRefDao.delete(
            TrackTagCrossRef(
                trackId = track.uri, // TODO: Make it more obvious that the URI is what must be used
                tagId = tag.id
            ) // TODO: How to abstract this?
        )
    }

}