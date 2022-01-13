package com.hfad.tagalong.presentation.ui.singletrack

import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.tag_domain.Tag

sealed class SingleTrackEvent {

    data class InitTrackEvent(val track: Track) : SingleTrackEvent()

    data class AddTagEvent(val tagName: String) : SingleTrackEvent()

    data class DeleteTagEvent(val tag: Tag) : SingleTrackEvent()

}
