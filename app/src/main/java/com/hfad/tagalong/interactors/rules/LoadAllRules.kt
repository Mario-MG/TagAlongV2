package com.hfad.tagalong.interactors.rules

import com.hfad.tagalong.cache.dao.RuleDao
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Rule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class LoadAllRules(
    private val ruleDao: RuleDao,
    private val ruleEntityMapper: RuleEntityMapper
) {

    fun execute(): Flow<DataState<List<Rule>>> = flow {
        try {
            emit(DataState.Loading)

            val rules = getAllRules()

            emit(DataState.Success(rules))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun getAllRules(): List<Rule> {
        return ruleEntityMapper.toDomainList(ruleDao.getAll())
    }

}