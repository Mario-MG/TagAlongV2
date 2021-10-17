package com.hfad.tagalong.network.model

import com.google.gson.annotations.SerializedName

data class GetTokenResponse (
    @SerializedName("access_token") var accessToken : String,
    @SerializedName("token_type") var tokenType : String,
    @SerializedName("expires_in") var expiresIn : Int,
    @SerializedName("scope") var scope : String,
    @SerializedName("refresh_token") var refreshToken : String
)