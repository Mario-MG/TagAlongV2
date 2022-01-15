package com.hfad.tagalong.presentation.ui.playlisttracks

import com.hfad.tagalong.playlist_domain.Playlist

sealed class PlaylistTracksEvent {

    data class InitPlaylistTracksEvent(val playlist: Playlist) : PlaylistTracksEvent()

    object NextPageEvent : PlaylistTracksEvent()

}
