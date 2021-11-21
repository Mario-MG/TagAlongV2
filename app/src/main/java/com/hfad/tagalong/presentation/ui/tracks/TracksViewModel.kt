package com.hfad.tagalong.presentation.ui.tracks

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hfad.tagalong.domain.model.Track

abstract class TracksViewModel : ViewModel() {

    val tracks = mutableStateListOf<Track>()

    val loading = mutableStateOf(false)

}