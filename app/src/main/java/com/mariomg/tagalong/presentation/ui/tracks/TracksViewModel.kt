package com.mariomg.tagalong.presentation.ui.tracks

import com.mariomg.tagalong.presentation.ui.BaseViewModel
import com.mariomg.tagalong.track_domain.Track

abstract class TracksViewModel : BaseViewModel() {

    abstract val tracks: List<Track>

    abstract var loading: Boolean
        protected set

    abstract val screenTitle: String // TODO: Move this to the Fragment?

}