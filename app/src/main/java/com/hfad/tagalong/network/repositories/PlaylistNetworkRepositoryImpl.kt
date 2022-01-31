package com.hfad.tagalong.network.repositories

import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import com.hfad.tagalong.network.util.AuthManager
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.playlist_interactors_core.repositories.PlaylistNetworkRepository
import com.hfad.tagalong.track_domain.Track

class PlaylistNetworkRepositoryImpl(
    private val playlistService: RetrofitPlaylistService,
    private val playlistDtoMapper: PlaylistDtoMapper,
    private val authManager: AuthManager
) : PlaylistNetworkRepository {

    override suspend fun addTracksToPlaylist(tracks: List<Track>, playlist: Playlist) {
        playlistService.addTracksToPlaylist(
            auth = "Bearer ${authManager.accessToken()}",
            playlistId = playlist.id,
            trackUris = tracks.map(Track::uri).joinToString(",") // TODO: Handle limit of 100 tracks
        )
    }

}