package com.mariomg.tagalong.playlist_network.repositories

import com.google.gson.JsonObject
import com.mariomg.tagalong.playlist_network.services.RetrofitPlaylistService
import com.mariomg.tagalong.playlist_network.model.PlaylistDtoMapper
import com.mariomg.tagalong.network_core.util.AuthManager
import com.mariomg.tagalong.network_core.util.UserManager
import com.mariomg.tagalong.playlist_domain.Playlist
import com.mariomg.tagalong.playlist_domain.PlaylistInfo
import com.mariomg.tagalong.playlist_interactors_core.repositories.PlaylistNetworkRepository
import com.mariomg.tagalong.track_domain.Track

class PlaylistNetworkRepositoryImpl(
    private val playlistService: RetrofitPlaylistService,
    private val playlistDtoMapper: PlaylistDtoMapper,
    private val authManager: AuthManager,
    private val userManager: UserManager
) : PlaylistNetworkRepository {

    override suspend fun getPlaylists(offset: Int, limit: Int): List<Playlist> {
        return playlistDtoMapper.mapToDomainModelList(
            playlistService.getList(
                auth = "Bearer ${authManager.accessToken()}",
                offset = offset,
                limit = limit
            ).items
        )
    }

    override suspend fun create(playlistInfo: PlaylistInfo): Playlist {
        return playlistDtoMapper.mapToDomainModel(
            playlistService.create(
                auth = "Bearer ${authManager.accessToken()}",
                userId = userManager.userId(),
                body = JsonObject().apply { addProperty("name", playlistInfo.name) }
            )
        )
    }

    override suspend fun addTracksToPlaylist(tracks: List<Track>, playlist: Playlist) {
        playlistService.addTracksToPlaylist(
            auth = "Bearer ${authManager.accessToken()}",
            playlistId = playlist.id,
            trackUris = tracks.map(Track::uri).joinToString(",") // TODO: Handle limit of 100 tracks
        )
    }

}