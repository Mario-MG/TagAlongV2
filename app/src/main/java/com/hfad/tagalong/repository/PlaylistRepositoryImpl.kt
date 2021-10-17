package com.hfad.tagalong.repository

import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.models.PlaylistDto
import com.hfad.tagalong.network.models.PlaylistListDto

class PlaylistRepositoryImpl(
    private val playlistService: RetrofitPlaylistService // TODO: Should this dependency be an abstraction?
): PlaylistRepository {

    override suspend fun getById(token: String, id: String): PlaylistDto { // TODO: Change to domain model
        return playlistService.getById(token = token, id = id)
    }

    override suspend fun getList(token: String, limit: Int, offset: Int): PlaylistListDto { // TODO: Change to domain model
        return playlistService.getList(token = token, limit = limit, offset = offset)
    }

}