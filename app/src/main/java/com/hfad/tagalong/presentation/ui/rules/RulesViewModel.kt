package com.hfad.tagalong.presentation.ui.rules

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.presentation.ui.rules.RulesEvent.CreateNewRuleEvent
import com.hfad.tagalong.presentation.ui.rules.RulesEvent.LoadRulesEvent
import com.hfad.tagalong.repository.RuleRepository
import com.hfad.tagalong.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RulesViewModel
@Inject
constructor(
    private val ruleRepository: RuleRepository,
    private val tagRepository: TagRepository
) : ViewModel() {

    val loading = mutableStateOf(false)

    val rules = mutableStateListOf<Rule>()

    private lateinit var allTags: List<Tag>

    init {
        onTriggerEvent(LoadRulesEvent)
    }

    fun onTriggerEvent(event: RulesEvent) {
        viewModelScope.launch {
            when (event) {
                is LoadRulesEvent -> {
                    loadAllRules()
                    loadAllTags()
                }
                is CreateNewRuleEvent -> {
                    createNewRule()
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

    private suspend fun loadAllTags() {
        allTags = tagRepository.getAll()
    }

    private suspend fun createNewRule() { // TODO: This is a testing implementation
        val testRule = Rule(
            playlistId = "testPlaylistId",
            optionality = false,
            autoUpdate = true,
            tags = listOf(allTags[0], allTags[2])
        )
        ruleRepository.createNewRule(testRule)
        loadAllRules()
    }

}