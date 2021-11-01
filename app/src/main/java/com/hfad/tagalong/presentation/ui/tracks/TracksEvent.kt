package com.hfad.tagalong.presentation.ui.tracks

sealed class TracksEvent {

    data class FirstPageEvent(val playlistId: String) : TracksEvent()

    data class NextPageEvent(val playlistId: String) : TracksEvent()

}
