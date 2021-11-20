package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.Track

interface TrackRepository {

    suspend fun getItemsInPlaylist(token: String, playlistId: String, limit: Int = 20, offset: Int = 0): List<Track>

    suspend fun getTrack(token: String, trackId: String): Track

    suspend fun getTracksForTag(tagId: Long): List<Track>

}