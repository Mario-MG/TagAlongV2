package com.hfad.tagalong.presentation.ui.tracks

sealed class TagTracksEvent {

    data class LoadTagTracksEvent(val tagId: Long): TagTracksEvent()

}
