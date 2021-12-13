package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track

interface TrackRepository {

    suspend fun getItemsInPlaylist(auth: String, playlist: Playlist, limit: Int = 20, offset: Int = 0): List<Track>

    suspend fun getTrack(auth: String, trackId: String): Track

    suspend fun getTracksForTag(tag: Tag): List<Track>

    suspend fun getTracksWithAnyOfTheTags(tags: List<Tag>): List<Track>

    suspend fun getTracksWithAllOfTheTags(tags: List<Tag>): List<Track>

}