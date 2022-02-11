package com.hfad.tagalong.auth_interactors

import com.hfad.tagalong.auth_interactors_core.repositories.AuthNetworkRepository
import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.DataState.Error
import com.hfad.tagalong.interactors_core.data.DataState.Loading
import com.hfad.tagalong.interactors_core.data.DataState.Success
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RefreshSession(
    private val authNetworkRepository: AuthNetworkRepository,
    private val networkErrorMapper: ErrorMapper
) {

    fun execute(): Flow<DataState<Unit>> = flow {
        try {
            emit(Loading(true))

            authNetworkRepository.refresh()

            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(networkErrorMapper.parseError(e)))
        } finally {
            emit(Loading(false))
        }
    }

}