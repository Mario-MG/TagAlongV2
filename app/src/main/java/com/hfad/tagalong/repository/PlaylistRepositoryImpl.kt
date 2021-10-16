package com.hfad.tagalong.repository

import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.models.PlaylistDto

class PlaylistRepositoryImpl(
    private val playlistService: RetrofitPlaylistService // TODO: Should this dependency be an abstraction?
): PlaylistRepository {

    override suspend fun get(token: String, id: String): PlaylistDto { // TODO: Change to domain model
        return playlistService.get(token = token, id = id)
    }

}