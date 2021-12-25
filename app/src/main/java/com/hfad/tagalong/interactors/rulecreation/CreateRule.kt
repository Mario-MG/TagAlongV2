package com.hfad.tagalong.interactors.rulecreation

import com.hfad.tagalong.cache.dao.RuleDao
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.domain.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateRule(
    private val ruleDao: RuleDao,
    private val ruleEntityMapper: RuleEntityMapper
) {

    fun execute(
        playlist: Playlist,
        optionality: Boolean,
        autoUpdate: Boolean,
        tags: List<Tag>
    ): Flow<DataState<Rule>> = flow {
        try {
            emit(DataState.Loading)

            val rule = createNewRule(
                playlist = playlist,
                optionality = optionality,
                autoUpdate = autoUpdate,
                tags = tags
            )

            emit(DataState.Success(rule))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun createNewRule(
        playlist: Playlist,
        optionality: Boolean,
        autoUpdate: Boolean,
        tags: List<Tag>
    ): Rule {
        val rule = Rule(
            playlist = playlist,
            optionality = optionality,
            autoUpdate = autoUpdate,
            tags = tags
        )
        val ruleId = ruleDao.insert(ruleEntityMapper.mapFromDomainModel(rule))
        return ruleEntityMapper.mapToDomainModel(ruleDao.getById(ruleId))
    }

}