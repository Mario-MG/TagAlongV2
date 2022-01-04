package com.hfad.tagalong.presentation.ui.rules

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.interactors.rules.LoadAllRules
import com.hfad.tagalong.presentation.ui.BaseViewModel
import com.hfad.tagalong.presentation.ui.rules.RulesEvent.LoadRulesEvent
import com.hfad.tagalong.presentation.util.DialogQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RulesViewModel
@Inject
constructor(
    private val loadAllRules: LoadAllRules
) : BaseViewModel() {

    val loading = mutableStateOf(false)

    val rules = mutableStateListOf<Rule>()

    override val dialogQueue = DialogQueue()

    init {
        onTriggerEvent(LoadRulesEvent)
    }

    fun onTriggerEvent(event: RulesEvent) {
        viewModelScope.launch {
            when (event) {
                is LoadRulesEvent -> {
                    loadAllRules()
                }
            }
        }
    }

    private fun loadAllRules() {
        loadAllRules
            .execute()
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { rules ->
                    this.rules.clear()
                    this.rules.addAll(rules)
                }

                dataState.error?.let(::appendGenericErrorToQueue)
            }
            .launchIn(viewModelScope)
    }

}