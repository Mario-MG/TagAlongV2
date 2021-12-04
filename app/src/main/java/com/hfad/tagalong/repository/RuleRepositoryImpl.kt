package com.hfad.tagalong.repository

import com.hfad.tagalong.cache.dao.RuleDao
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.domain.model.Rule

class RuleRepositoryImpl(
    private val ruleDao: RuleDao,
    private val ruleEntityMapper: RuleEntityMapper
) : RuleRepository {

    override suspend fun getAll(): List<Rule> {
        return ruleEntityMapper.toDomainList(ruleDao.getAll())
    }

    override suspend fun createNewRule(rule: Rule) {
        ruleDao.insert(ruleEntityMapper.mapFromDomainModel(rule))
    }

}