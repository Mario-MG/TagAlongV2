package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.model.PlaylistDtoMapper

class PlaylistRepositoryImpl(
    private val playlistService: RetrofitPlaylistService,
    private val playlistMapper: PlaylistDtoMapper
): PlaylistRepository {

    override suspend fun getById(token: String, id: String): Playlist {
        return playlistMapper.mapToDomainModel(playlistService.getById(token = token, id = id))
    }

    override suspend fun getList(token: String, limit: Int, offset: Int): List<Playlist> {
        return playlistMapper.toDomainList(playlistService.getList(token = token, limit = limit, offset = offset).items)
    }

}