package com.hfad.tagalong.cache.repositories

import com.hfad.tagalong.cache.dao.PlaylistDao
import com.hfad.tagalong.cache.model.PlaylistEntityMapper
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.playlist_interactors_core.repositories.PlaylistCacheRepository

class PlaylistCacheRepositoryImpl(
    private val playlistDao: PlaylistDao,
    private val playlistEntityMapper: PlaylistEntityMapper
) : PlaylistCacheRepository {

    override suspend fun create(playlist: Playlist) {
        playlistDao.insert(playlistEntityMapper.mapFromDomainModel(playlist))
    }

}