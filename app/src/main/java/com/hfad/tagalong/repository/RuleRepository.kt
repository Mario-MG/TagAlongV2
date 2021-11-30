package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.Rule

interface RuleRepository {

    suspend fun getAll(): List<Rule>

    suspend fun createNewRule(rule: Rule)

}