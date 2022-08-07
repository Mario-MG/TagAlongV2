package com.mariomg.tagalong.settings_interactors

import com.mariomg.tagalong.interactors_core.data.DataState
import com.mariomg.tagalong.interactors_core.data.DataState.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import com.mariomg.tagalong.settings_interactors_core.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveStayLoggedIn(
    private val settingsRepository: SettingsRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(stayLoggedIn: Boolean): Flow<DataState<Unit>> = flow {
        try {
            emit(Loading(true))

            settingsRepository.saveStayLoggedIn(stayLoggedIn)

            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}