package com.hfad.tagalong.interactors.rules

import com.hfad.tagalong.cache.dao.RuleDao
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.interactors.data.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class LoadAllRules(
    private val ruleDao: RuleDao,
    private val ruleEntityMapper: RuleEntityMapper,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(): Flow<DataState<List<Rule>>> = flow {
        try {
            emit(DataState.Loading)

            val rules = getAllRules()

            emit(DataState.Success(rules))
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        }
    }

    private suspend fun getAllRules(): List<Rule> {
        return ruleEntityMapper.toDomainList(ruleDao.getAll())
    }

}