package com.hfad.tagalong.rule_interactors

import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.rule_domain.Rule
import com.hfad.tagalong.rule_interactors_core.repositories.RuleCacheRepository
import com.hfad.tagalong.tag_domain.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadRulesForTags(
    private val ruleCacheRepository: RuleCacheRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(
        newTag: Tag,
        originalTags: List<Tag>
    ): Flow<DataState<List<Rule>>> = flow {
        try {
            emit(Loading(true))

            val rules = ruleCacheRepository.getRulesForTags(newTag = newTag, originalTags = originalTags)

            emit(Success(rules))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}