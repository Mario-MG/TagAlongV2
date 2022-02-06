package com.hfad.tagalong.network.repositories

import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.model.PlaylistItem
import com.hfad.tagalong.network.model.TrackDtoMapper
import com.hfad.tagalong.network.util.AuthManager
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.track_domain.Track
import com.hfad.tagalong.track_interactors_core.repositories.TrackNetworkRepository

class TrackNetworkRepositoryImpl(
    private val trackService: RetrofitTrackService,
    private val trackDtoMapper: TrackDtoMapper,
    private val authManager: AuthManager
) : TrackNetworkRepository {

    override suspend fun getTracksInPlaylist(playlist: Playlist, offset: Int, limit: Int): List<Track> {
        return trackDtoMapper.toDomainList(trackService.getItemsInPlaylist(
            auth = "Bearer ${authManager.accessToken()}",
            playlistId = playlist.id,
            offset = offset,
            limit = limit
        ).items.map(PlaylistItem::track))
    }

}