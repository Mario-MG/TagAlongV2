package com.hfad.tagalong.presentation.ui.tracks

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.presentation.ui.BaseViewModel

abstract class TracksViewModel : BaseViewModel() {

    val tracks = mutableStateListOf<Track>()

    val loading = mutableStateOf(false)

    abstract val screenTitle: MutableState<String> // TODO: Move this to the Fragment?

}