package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track

interface TagRepository {

    suspend fun getAll(): List<Tag>

    suspend fun getAllForTrack(track: Track): List<Tag>

//    suspend fun insert(vararg tags: Tag)
//
//    suspend fun delete(vararg tags: Tag)

}