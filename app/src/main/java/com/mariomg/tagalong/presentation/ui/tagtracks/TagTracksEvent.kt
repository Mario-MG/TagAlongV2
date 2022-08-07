package com.mariomg.tagalong.presentation.ui.tagtracks

import com.mariomg.tagalong.tag_domain.Tag

sealed class TagTracksEvent {

    data class InitTagTracksEvent(val tag: Tag) : TagTracksEvent()

    object LoadTagTracksEvent : TagTracksEvent()

}
