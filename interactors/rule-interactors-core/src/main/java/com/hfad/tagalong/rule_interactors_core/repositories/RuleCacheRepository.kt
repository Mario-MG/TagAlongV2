package com.hfad.tagalong.rule_interactors_core.repositories

import com.hfad.tagalong.rule_domain.Rule
import com.hfad.tagalong.rule_domain.RuleInfo
import com.hfad.tagalong.tag_domain.Tag

interface RuleCacheRepository {

    suspend fun getAll(): List<Rule>

    suspend fun getRulesForTags(newTag: Tag, originalTags: List<Tag>): List<Rule>

    suspend fun create(ruleInfo: RuleInfo): Rule

}