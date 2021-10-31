package com.hfad.tagalong.presentation.ui.playlists

sealed class PlaylistsEvent {

    object FirstPageEvent : PlaylistsEvent()

    object NextPageEvent : PlaylistsEvent()

}
