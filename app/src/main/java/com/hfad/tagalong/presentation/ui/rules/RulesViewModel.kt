package com.hfad.tagalong.presentation.ui.rules

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.interactors.data.on
import com.hfad.tagalong.interactors.rules.LoadAllRules
import com.hfad.tagalong.presentation.ui.BaseViewModel
import com.hfad.tagalong.presentation.ui.rules.RulesEvent.LoadRulesEvent
import com.hfad.tagalong.presentation.util.DialogQueue
import com.hfad.tagalong.rule_domain.Rule
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