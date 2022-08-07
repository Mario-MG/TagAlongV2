package com.mariomg.tagalong.network_core.model

import com.google.gson.annotations.SerializedName

data class ImageDto (
    @SerializedName("height") var height : Int?,
    @SerializedName("url") var url : String,
    @SerializedName("width") var width : Int?
)
