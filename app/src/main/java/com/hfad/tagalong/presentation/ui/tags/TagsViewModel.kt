package com.hfad.tagalong.presentation.ui.tags

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hfad.tagalong.domain.model.Tag

class TagsViewModel : ViewModel() {

    val tags = mutableStateListOf<Tag>()

    val loading = mutableStateOf(false)

}