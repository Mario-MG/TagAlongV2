package com.hfad.tagalong.settings_interactors

import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.*
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.settings_interactors_core.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadStayLoggedIn(
    private val settingsRepository: SettingsRepository,
    private val cacheErrorMapper: ErrorMapper
) {

    fun execute(): Flow<DataState<Boolean>> = flow {
        try {
            emit(Loading(true))

            val stayLoggedIn = settingsRepository.loadStayLoggedIn()

            emit(Success(stayLoggedIn))
        } catch (e: Exception) {
            emit(Error(cacheErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}