package com.hfad.tagalong.presentation.ui.tags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.interactors.data.on
import com.hfad.tagalong.interactors.tags.LoadAllTags
import com.hfad.tagalong.presentation.ui.BaseViewModel
import com.hfad.tagalong.presentation.ui.tags.TagsEvent.LoadTagsEvent
import com.hfad.tagalong.presentation.util.DialogQueue
import com.hfad.tagalong.tag_domain.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModel
@Inject
constructor(
    private val loadAllTags: LoadAllTags
) : BaseViewModel() {

    val tags = mutableStateListOf<Tag>()

    var loading by mutableStateOf(false)
        private set

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
            .on(
                loading = ::loading::set,
                success = { tags ->
                    this.tags.clear()
                    this.tags.addAll(tags)
                },
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
    }

}