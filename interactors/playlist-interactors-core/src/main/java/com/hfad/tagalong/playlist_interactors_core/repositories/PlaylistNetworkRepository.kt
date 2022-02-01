package com.hfad.tagalong.playlist_interactors_core.repositories

import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.track_domain.Track

interface PlaylistNetworkRepository {

    suspend fun getPlaylists(offset: Int, limit: Int): List<Playlist>

    suspend fun addTracksToPlaylist(tracks: List<Track>, playlist: Playlist)

}