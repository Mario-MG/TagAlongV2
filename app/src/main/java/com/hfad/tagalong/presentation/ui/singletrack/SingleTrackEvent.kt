package com.hfad.tagalong.presentation.ui.singletrack

import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track

sealed class SingleTrackEvent {

    data class InitTrackEvent(val track: Track) : SingleTrackEvent()

    data class AddTagEvent(val tagName: String) : SingleTrackEvent()

    data class DeleteTagEvent(val tag: Tag) : SingleTrackEvent()

}
