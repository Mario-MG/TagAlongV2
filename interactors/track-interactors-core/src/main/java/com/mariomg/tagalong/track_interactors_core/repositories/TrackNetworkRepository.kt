package com.mariomg.tagalong.track_interactors_core.repositories

import com.mariomg.tagalong.playlist_domain.Playlist
import com.mariomg.tagalong.track_domain.Track

interface TrackNetworkRepository {

    suspend fun getTracksInPlaylist(playlist: Playlist, offset: Int, limit: Int): List<Track>

}