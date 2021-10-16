package com.hfad.tagalong.network.models

import com.google.gson.annotations.SerializedName

data class Image (
    @SerializedName("height") var height : Int?,
    @SerializedName("url") var url : String,
    @SerializedName("width") var width : Int?
)
