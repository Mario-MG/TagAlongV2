package com.hfad.tagalong.repository

import com.google.gson.JsonObject
import com.hfad.tagalong.cache.dao.PlaylistDao
import com.hfad.tagalong.cache.model.PlaylistEntityMapper
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.model.PlaylistDtoMapper

class PlaylistRepositoryImpl(
    private val playlistService: RetrofitPlaylistService,
    private val playlistDtoMapper: PlaylistDtoMapper,
    private val playlistDao: PlaylistDao,
    private val playlistEntityMapper: PlaylistEntityMapper
): PlaylistRepository {

    override suspend fun getById(auth: String, id: String): Playlist {
        return playlistDtoMapper.mapToDomainModel(playlistService.getById(auth = auth, id = id))
    }

    override suspend fun getList(auth: String, limit: Int, offset: Int): List<Playlist> {
        return playlistDtoMapper.toDomainList(playlistService.getList(auth = auth, limit = limit, offset = offset).items)
    }

    override suspend fun create(auth: String, userId: String, playlist: Playlist): Playlist {
        val newPlaylist = playlistDtoMapper.mapToDomainModel(
            playlistService.create(
                auth = auth,
                userId = userId,
                body = JsonObject().apply { addProperty("name", playlist.name) }
            )
        )
        playlistDao.insert(playlistEntityMapper.mapFromDomainModel(newPlaylist))
        return newPlaylist
    }

    override suspend fun addTracksToPlaylist(auth: String, playlist: Playlist, tracks: List<Track>) {
        playlistService.addTracksToPlaylist(auth = auth, playlistId = playlist.id, trackUris = tracks.map(Track::uri).joinToString(",")) // TODO: Handle limit of 100 tracks
    }

}