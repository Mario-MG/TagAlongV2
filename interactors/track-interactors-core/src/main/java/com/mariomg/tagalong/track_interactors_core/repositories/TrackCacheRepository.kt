package com.mariomg.tagalong.track_interactors_core.repositories

import com.mariomg.tagalong.rule_domain.Rule
import com.mariomg.tagalong.tag_domain.Tag
import com.mariomg.tagalong.track_domain.Track

interface TrackCacheRepository {

    suspend fun getAllTracksForTag(tag: Tag): List<Track>

    suspend fun getAllTracksForRule(rule: Rule): List<Track>

    suspend fun saveTrack(track: Track)

    suspend fun addTagToTrack(tag: Tag, track: Track)

    suspend fun deleteTagFromTrack(tag: Tag, track: Track)

}