package com.hfad.tagalong.presentation.ui.singletrack

import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.track_domain.Track

sealed class SingleTrackEvent {

    data class InitTrackEvent(val track: Track) : SingleTrackEvent()

    data class AddTagEvent(val tagName: String) : SingleTrackEvent()

    data class DeleteTagEvent(val tag: Tag) : SingleTrackEvent()

}
