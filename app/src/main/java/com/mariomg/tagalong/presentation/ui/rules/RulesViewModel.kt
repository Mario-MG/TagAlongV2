package com.mariomg.tagalong.presentation.ui.rules

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.mariomg.tagalong.interactors_core.util.on
import com.mariomg.tagalong.presentation.ui.BaseViewModel
import com.mariomg.tagalong.presentation.ui.rules.RulesEvent.LoadRulesEvent
import com.mariomg.tagalong.presentation.util.DialogQueue
import com.mariomg.tagalong.rule_domain.Rule
import com.mariomg.tagalong.rule_interactors.LoadAllRules
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RulesViewModel
@Inject
constructor(
    private val loadAllRules: LoadAllRules
) : BaseViewModel() {

    var loading by mutableStateOf(false)
        private set

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
            .on(
                loading = ::loading::set,
                success = { rules ->
                    this.rules.clear()
                    this.rules.addAll(rules)
                },
                error = ::appendGenericErrorToQueue
            )
            .launchIn(viewModelScope)
    }

}