package com.hfad.tagalong.presentation.ui.tags

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.presentation.ui.tags.TagsEvent.LoadTagsEvent
import com.hfad.tagalong.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModel
@Inject
constructor (
    private val tagRepository: TagRepository
) : ViewModel() {

    val tags = mutableStateListOf<Tag>()

    val loading = mutableStateOf(false)

    init {
        onTriggerEvent(LoadTagsEvent)
    }

    fun onTriggerEvent(event: TagsEvent) {
        viewModelScope.launch {
            when (event) {
                is LoadTagsEvent -> {
                    loadAllTags()
                }
            }
        }
    }

    private suspend fun loadAllTags() {
        loading.value = true
        val tags = tagRepository.getAll()
        this.tags.clear()
        this.tags.addAll(tags)
        loading.value = false
    }

}