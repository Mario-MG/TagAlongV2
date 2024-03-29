package com.mariomg.tagalong.auth_network.services

import com.mariomg.tagalong.network_core.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Header

interface RetrofitUserService {

    @GET("me")
    suspend fun getUserProfile(
        @Header("Authorization") auth: String
    ): UserDto

}