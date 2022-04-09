package com.hfad.tagalong.track_network.repositories

import com.hfad.tagalong.track_network.services.RetrofitTrackService
import com.hfad.tagalong.track_network.model.PlaylistItem
import com.hfad.tagalong.track_network.model.TrackDtoMapper
import com.hfad.tagalong.network_core.util.AuthManager
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.track_domain.Track
import com.hfad.tagalong.track_interactors_core.repositories.TrackNetworkRepository

class TrackNetworkRepositoryImpl(
    private val trackService: RetrofitTrackService,
    private val trackDtoMapper: TrackDtoMapper,
    private val authManager: AuthManager
) : TrackNetworkRepository {

    override suspend fun getTracksInPlaylist(playlist: Playlist, offset: Int, limit: Int): List<Track> {
        return trackDtoMapper.mapToDomainModelList(trackService.getItemsInPlaylist(
            auth = "Bearer ${authManager.accessToken()}",
            playlistId = playlist.id,
            offset = offset,
            limit = limit
        ).items.map(PlaylistItem::track))
    }

}