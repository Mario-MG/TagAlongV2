package com.mariomg.tagalong.cache.repositories

import com.mariomg.tagalong.cache.dao.PlaylistDao
import com.mariomg.tagalong.cache.model.PlaylistEntityMapper
import com.mariomg.tagalong.playlist_domain.Playlist
import com.mariomg.tagalong.playlist_interactors_core.repositories.PlaylistCacheRepository

class PlaylistCacheRepositoryImpl(
    private val playlistDao: PlaylistDao,
    private val playlistEntityMapper: PlaylistEntityMapper
) : PlaylistCacheRepository {

    override suspend fun create(playlist: Playlist) {
        playlistDao.insert(playlistEntityMapper.mapFromDomainModel(playlist))
    }

}