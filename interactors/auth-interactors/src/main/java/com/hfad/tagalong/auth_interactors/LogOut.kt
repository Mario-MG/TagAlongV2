package com.hfad.tagalong.auth_interactors

import com.hfad.tagalong.auth_interactors_core.repositories.AuthNetworkRepository
import com.hfad.tagalong.interactors_core.data.DataState
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LogOut(
    private val authNetworkRepository: AuthNetworkRepository,
    private val networkErrorMapper: ErrorMapper
) {

    fun execute(): Flow<DataState<Unit>> = flow {
        try {
            emit(DataState.Loading(true))

            authNetworkRepository.logOut()

            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(networkErrorMapper.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
        }
    }

}