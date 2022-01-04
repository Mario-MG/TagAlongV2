package com.hfad.tagalong.presentation.ui.tracks

import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.presentation.ui.BaseViewModel

abstract class TracksViewModel : BaseViewModel() {

    abstract val tracks: List<Track>

    abstract var loading: Boolean
        protected set

    abstract val screenTitle: String // TODO: Move this to the Fragment?

}