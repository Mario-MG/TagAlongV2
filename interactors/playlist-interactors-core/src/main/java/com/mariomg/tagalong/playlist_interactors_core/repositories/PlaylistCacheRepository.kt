package com.mariomg.tagalong.playlist_interactors_core.repositories

import com.mariomg.tagalong.playlist_domain.Playlist

interface PlaylistCacheRepository {

    suspend fun create(playlist: Playlist)

}