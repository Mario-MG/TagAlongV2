package com.hfad.tagalong.presentation.ui.tracks

sealed class PlaylistTracksEvent {

    data class FirstPageEvent(val playlistId: String) : PlaylistTracksEvent()

    data class NextPageEvent(val playlistId: String) : PlaylistTracksEvent()

}
