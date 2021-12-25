package com.hfad.tagalong.presentation.ui.playlisttracks

import com.hfad.tagalong.domain.model.Playlist

sealed class PlaylistTracksEvent {

    data class InitPlaylistTracksEvent(val playlist: Playlist) : PlaylistTracksEvent()

    object NextPageEvent : PlaylistTracksEvent()

}
