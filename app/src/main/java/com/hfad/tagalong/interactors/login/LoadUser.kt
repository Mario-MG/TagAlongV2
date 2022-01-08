package com.hfad.tagalong.interactors.login

import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.domain.model.User
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.network.RetrofitUserService
import com.hfad.tagalong.network.model.UserDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadUser(
    private val userService: RetrofitUserService,
    private val userDtoMapper: UserDtoMapper,
    private val networkErrorHandler: ErrorHandler
) {

    fun execute(token: Token): Flow<DataState<User>> = flow {
        try {
            emit(DataState.Loading(true))

            val user = getUser(auth = getAuthorizationHeader(token))

            emit(DataState.Success(user))
        } catch (e: Exception) {
            emit(DataState.Error(networkErrorHandler.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
        }
    }

    private fun getAuthorizationHeader(token: Token) = "Bearer ${token.accessToken}"

    private suspend fun getUser(auth: String): User {
        return userDtoMapper.mapToDomainModel(userService.getUserProfile(auth = auth))
    }

}