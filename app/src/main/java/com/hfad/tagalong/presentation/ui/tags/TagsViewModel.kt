package com.hfad.tagalong.presentation.ui.tags

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.interactors.tags.LoadAllTags
import com.hfad.tagalong.presentation.ui.BaseViewModel
import com.hfad.tagalong.presentation.ui.tags.TagsEvent.LoadTagsEvent
import com.hfad.tagalong.presentation.util.DialogQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModel
@Inject
constructor (
    private val loadAllTags: LoadAllTags
) : BaseViewModel() {

    val tags = mutableStateListOf<Tag>()

    val loading = mutableStateOf(false)

    override val dialogQueue = DialogQueue()

    fun onTriggerEvent(event: TagsEvent) {
        viewModelScope.launch {
            when (event) {
                is LoadTagsEvent -> {
                    loadAllTags()
                }
            }
        }
    }

    private fun loadAllTags() {
        loadAllTags
            .execute()
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { tags ->
                    this.tags.clear()
                    this.tags.addAll(tags)
                }

                dataState.error?.let(::appendGenericErrorToQueue)
            }
            .launchIn(viewModelScope)
    }

}