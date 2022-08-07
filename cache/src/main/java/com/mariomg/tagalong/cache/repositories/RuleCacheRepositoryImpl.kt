package com.mariomg.tagalong.cache.repositories

import com.mariomg.tagalong.cache.dao.RuleDao
import com.mariomg.tagalong.cache.model.RuleEntityMapper
import com.mariomg.tagalong.rule_domain.Rule
import com.mariomg.tagalong.rule_domain.RuleInfo
import com.mariomg.tagalong.rule_interactors_core.repositories.RuleCacheRepository
import com.mariomg.tagalong.tag_domain.Tag

class RuleCacheRepositoryImpl(
    private val ruleDao: RuleDao,
    private val ruleEntityMapper: RuleEntityMapper
) : RuleCacheRepository {

    override suspend fun getAll(): List<Rule> {
        return ruleEntityMapper.mapToDomainModelList(ruleDao.getAll())
    }

    override suspend fun getRulesForTags(newTag: Tag, originalTags: List<Tag>): List<Rule> {
        return ruleEntityMapper.mapToDomainModelList(
            ruleDao.getRulesFulfilledByTagIds(newTag.id, *originalTags.map(Tag::id).toLongArray())
        )
    }

    override suspend fun create(ruleInfo: RuleInfo): Rule {
        val ruleId = ruleDao.insert(ruleEntityMapper.mapFromDomainModel(ruleInfo))
        return ruleEntityMapper.mapToDomainModel(ruleDao.getById(ruleId))
    }

}