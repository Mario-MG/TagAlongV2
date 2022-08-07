package com.mariomg.tagalong.presentation.ui.playlists

sealed class PlaylistsEvent {

    object FirstPageEvent : PlaylistsEvent()

    object NextPageEvent : PlaylistsEvent()

}
