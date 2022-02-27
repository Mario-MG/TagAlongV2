package com.hfad.tagalong.auth_network.services

import com.hfad.tagalong.network_core.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Header

interface RetrofitUserService {

    @GET("me")
    suspend fun getUserProfile(
        @Header("Authorization") auth: String
    ): UserDto

}