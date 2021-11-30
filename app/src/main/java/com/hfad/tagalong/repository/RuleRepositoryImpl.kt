package com.hfad.tagalong.repository

import com.hfad.tagalong.cache.dao.RuleDao
import com.hfad.tagalong.cache.dao.RuleTagCrossRefDao
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.cache.model.RuleTagCrossRef
import com.hfad.tagalong.domain.model.Rule

class RuleRepositoryImpl(
    private val ruleDao: RuleDao,
    private val ruleEntityMapper: RuleEntityMapper,
    private val ruleTagCrossRefDao: RuleTagCrossRefDao
) : RuleRepository {

    override suspend fun getAll(): List<Rule> {
        return ruleEntityMapper.toDomainList(ruleDao.getAll())
    }

    override suspend fun createNewRule(rule: Rule) {
        val ruleWithTags = ruleEntityMapper.mapFromDomainModel(rule)
        val ruleId = ruleDao.insert(ruleWithTags.rule)
        ruleTagCrossRefDao.insert(*ruleWithTags.tags.map { tag ->
            RuleTagCrossRef(
                ruleId = ruleId,
                tagId = tag.id
            )
        }.toTypedArray())
    }

}