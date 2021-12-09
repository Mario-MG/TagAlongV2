package com.hfad.tagalong.presentation.ui.rules

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.presentation.ui.rules.RulesEvent.LoadRulesEvent
import com.hfad.tagalong.repository.RuleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RulesViewModel
@Inject
constructor(
    private val ruleRepository: RuleRepository
) : ViewModel() {

    val loading = mutableStateOf(false)

    val rules = mutableStateListOf<Rule>()

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

    private suspend fun loadAllRules() {
        loading.value = true
        val rules = ruleRepository.getAll()
        this.rules.clear()
        this.rules.addAll(rules)
        loading.value = false
    }

}