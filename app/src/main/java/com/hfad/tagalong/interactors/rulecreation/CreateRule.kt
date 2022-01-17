package com.hfad.tagalong.interactors.rulecreation

import com.hfad.tagalong.cache.dao.RuleDao
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.rule_domain.Rule
import com.hfad.tagalong.tag_domain.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateRule(
    private val ruleDao: RuleDao,
    private val ruleEntityMapper: RuleEntityMapper,
    private val cacheErrorHandler: ErrorHandler
) {

    fun execute(
        playlist: Playlist,
        optionality: Boolean,
        autoUpdate: Boolean,
        tags: List<Tag>
    ): Flow<DataState<Rule>> = flow {
        try {
            emit(DataState.Loading(true))

            val rule = createNewRule(
                playlist = playlist,
                optionality = optionality,
                autoUpdate = autoUpdate,
                tags = tags
            )

            emit(DataState.Success(rule))
        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
        }
    }

    private suspend fun createNewRule(
        playlist: Playlist,
        optionality: Boolean,
        autoUpdate: Boolean,
        tags: List<Tag>
    ): Rule {
        val rule = Rule(
            id = 0,
            playlist = playlist,
            optionality = optionality,
            autoUpdate = autoUpdate,
            tags = tags
        )
        val ruleId = ruleDao.insert(ruleEntityMapper.mapFromDomainModel(rule)) // TODO: Refactor into function taking all parameters except for id
        return ruleEntityMapper.mapToDomainModel(ruleDao.getById(ruleId))
    }

}