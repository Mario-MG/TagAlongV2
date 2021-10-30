package com.hfad.tagalong.repository

import android.database.sqlite.SQLiteConstraintException
import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.dao.TrackTagCrossRefDao
import com.hfad.tagalong.cache.database.MainDatabase
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.cache.model.TrackTagCrossRef
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track

class TrackTagRepositoryImpl(
    private val tagDao: TagDao,
    private val trackDao: TrackDao,
    private val trackTagCrossRefDao: TrackTagCrossRefDao,
    private val tagEntityMapper: TagEntityMapper,
    private val trackEntityMapper: TrackEntityMapper
) : TrackTagRepository {

    override suspend fun addTagToTrack(tag: Tag, track: Track) {
        val newTagId = tagDao.insert(tagEntityMapper.mapFromDomainModel(tag)) // FIXME: Returns -1 if tag already exists ==> crashes!
        trackDao.insert(trackEntityMapper.mapFromDomainModel(track))
        trackTagCrossRefDao.insert(
            TrackTagCrossRef(
                trackId = track.id,
                tagId = newTagId
            ) // TODO: How to abstract this?
        )
    }

}