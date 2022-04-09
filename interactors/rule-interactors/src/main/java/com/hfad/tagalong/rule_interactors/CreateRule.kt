package com.hfad.tagalong.rule_interactors

import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.rule_domain.Rule
import com.hfad.tagalong.rule_domain.RuleInfo
import com.hfad.tagalong.rule_interactors_core.repositories.RuleCacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateRule(
    private val ruleCacheRepository: RuleCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(ruleInfo: RuleInfo): Flow<DataState<Rule>> = flow {
        try {
            emit(Loading(true))

            val rule = ruleCacheRepository.create(ruleInfo)

            emit(Success(rule))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}