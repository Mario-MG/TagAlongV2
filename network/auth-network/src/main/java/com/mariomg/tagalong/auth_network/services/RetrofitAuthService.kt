package com.mariomg.tagalong.auth_network.services

import com.mariomg.tagalong.auth_network.model.TokenDto
import retrofit2.http.*

interface RetrofitAuthService {

    @POST("token")
    @FormUrlEncoded
    suspend fun getNewToken(
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("client_id") clientId: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("code_verifier") codeVerifier: String,
        @Field("code") code: String
    ): TokenDto

    @POST("token")
    @FormUrlEncoded
    suspend fun refreshToken(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("client_id") clientId: String,
        @Field("refresh_token") refreshToken: String
    ): TokenDto

}