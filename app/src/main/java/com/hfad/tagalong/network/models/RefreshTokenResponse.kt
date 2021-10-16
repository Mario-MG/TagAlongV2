package com.hfad.tagalong.network.models

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse (
    @SerializedName("access_token") var accessToken : String,
    @SerializedName("token_type") var tokenType : String,
    @SerializedName("expires_in") var expiresIn : Int,
    @SerializedName("scope") var scope : String
)