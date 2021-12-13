package com.hfad.tagalong.presentation.ui.tracks

import com.hfad.tagalong.domain.model.Tag

sealed class TagTracksEvent {

    data class InitTagTracksEvent(val tag: Tag) : TagTracksEvent()

    object ReloadTagTracksEvent : TagTracksEvent()

}
