package com.hfad.tagalong.track_interactors_core.repositories

import com.hfad.tagalong.rule_domain.Rule
import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.track_domain.Track

interface TrackCacheRepository {

    suspend fun getAllTracksForTag(tag: Tag): List<Track>

    suspend fun getAllTracksForRule(rule: Rule): List<Track>

    suspend fun saveTrack(track: Track)

    suspend fun addTagToTrack(tag: Tag, track: Track)

    suspend fun deleteTagFromTrack(tag: Tag, track: Track)

}