package com.hfad.tagalong.presentation.ui.singletrack

import com.hfad.tagalong.domain.model.Tag

sealed class SingleTrackEvent {

    data class LoadTrackDetailsEvent(val trackId: String) : SingleTrackEvent()

    data class AddTagEvent(val tagName: String) : SingleTrackEvent()

    data class DeleteTagEvent(val tag: Tag) : SingleTrackEvent()

}
