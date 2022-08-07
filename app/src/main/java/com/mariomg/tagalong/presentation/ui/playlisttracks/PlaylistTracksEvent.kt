package com.mariomg.tagalong.presentation.ui.playlisttracks

import com.mariomg.tagalong.playlist_domain.Playlist

sealed class PlaylistTracksEvent {

    data class InitPlaylistTracksEvent(val playlist: Playlist) : PlaylistTracksEvent()

    object NextPageEvent : PlaylistTracksEvent()

}
