package com.hfad.tagalong.cache.repositories

import com.hfad.tagalong.cache.dao.RuleDao
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.rule_domain.Rule
import com.hfad.tagalong.rule_interactors_core.repositories.RuleCacheRepository
import com.hfad.tagalong.tag_domain.Tag

class RuleCacheRepositoryImpl(
    private val ruleDao: RuleDao,
    private val ruleEntityMapper: RuleEntityMapper
) : RuleCacheRepository {

    override suspend fun getRulesForTags(newTag: Tag, originalTags: List<Tag>): List<Rule> {
        return ruleEntityMapper.toDomainList(
            ruleDao.getRulesFulfilledByTagIds(newTag.id, *originalTags.map(Tag::id).toLongArray())
        )
    }

}