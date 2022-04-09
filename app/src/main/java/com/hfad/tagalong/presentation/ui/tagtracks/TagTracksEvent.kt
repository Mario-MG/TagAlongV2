package com.hfad.tagalong.presentation.ui.tagtracks

import com.hfad.tagalong.tag_domain.Tag

sealed class TagTracksEvent {

    data class InitTagTracksEvent(val tag: Tag) : TagTracksEvent()

    object LoadTagTracksEvent : TagTracksEvent()

}
