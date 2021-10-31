package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track

interface TrackTagRepository {

    suspend fun addTagToTrack(tag: Tag, track: Track)

    suspend fun deleteTagFromTrack(tag: Tag, track: Track)

}