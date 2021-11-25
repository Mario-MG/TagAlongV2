package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.domain.model.Track

interface PlaylistRepository {

    suspend fun getById(auth: String, id: String): Playlist

    suspend fun getList(auth: String, limit: Int = 20, offset: Int = 0): List<Playlist>

}