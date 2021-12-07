package com.hfad.tagalong.repository

import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.domain.model.Tag

interface RuleRepository {

    suspend fun getAll(): List<Rule>

    suspend fun createNewRule(rule: Rule)

    suspend fun getRulesFulfilledByTags(newTag: Tag, originalTags: List<Tag>): List<Rule>

}