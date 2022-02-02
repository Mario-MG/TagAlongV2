package com.hfad.tagalong.rule_interactors

import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.rule_domain.Rule
import com.hfad.tagalong.rule_interactors_core.repositories.RuleCacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadAllRules(
    private val ruleCacheRepository: RuleCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(): Flow<DataState<List<Rule>>> = flow {
        try {
            emit(Loading(true))

            val rules = ruleCacheRepository.getAll()

            emit(Success(rules))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}