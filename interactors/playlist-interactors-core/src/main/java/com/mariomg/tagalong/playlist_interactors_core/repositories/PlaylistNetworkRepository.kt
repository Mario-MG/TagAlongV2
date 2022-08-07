package com.mariomg.tagalong.playlist_interactors_core.repositories

import com.mariomg.tagalong.playlist_domain.Playlist
import com.mariomg.tagalong.playlist_domain.PlaylistInfo
import com.mariomg.tagalong.track_domain.Track

interface PlaylistNetworkRepository {

    suspend fun getPlaylists(offset: Int, limit: Int): List<Playlist>

    suspend fun create(playlistInfo: PlaylistInfo): Playlist

    suspend fun addTracksToPlaylist(tracks: List<Track>, playlist: Playlist)

}