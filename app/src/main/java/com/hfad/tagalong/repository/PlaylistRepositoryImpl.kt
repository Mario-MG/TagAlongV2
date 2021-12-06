package com.hfad.tagalong.repository

import com.google.gson.JsonObject
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.model.PlaylistDtoMapper

class PlaylistRepositoryImpl(
    private val playlistService: RetrofitPlaylistService,
    private val playlistMapper: PlaylistDtoMapper
): PlaylistRepository {

    override suspend fun getById(auth: String, id: String): Playlist {
        return playlistMapper.mapToDomainModel(playlistService.getById(auth = auth, id = id))
    }

    override suspend fun getList(auth: String, limit: Int, offset: Int): List<Playlist> {
        return playlistMapper.toDomainList(playlistService.getList(auth = auth, limit = limit, offset = offset).items)
    }

    override suspend fun create(auth: String, userId: String, playlist: Playlist): Playlist {
        return playlistMapper.mapToDomainModel(playlistService.create(auth = auth, userId = userId, body = JsonObject().apply { addProperty("name", playlist.name) }))
    }

}