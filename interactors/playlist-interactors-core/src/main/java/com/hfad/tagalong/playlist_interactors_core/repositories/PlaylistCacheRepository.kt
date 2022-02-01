package com.hfad.tagalong.playlist_interactors_core.repositories

import com.hfad.tagalong.playlist_domain.Playlist

interface PlaylistCacheRepository {

    suspend fun create(playlist: Playlist)

}