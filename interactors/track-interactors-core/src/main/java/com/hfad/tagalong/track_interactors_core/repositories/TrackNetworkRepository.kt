package com.hfad.tagalong.track_interactors_core.repositories

import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.track_domain.Track

interface TrackNetworkRepository {

    suspend fun getTracksInPlaylist(playlist: Playlist, offset: Int, limit: Int): List<Track>

}