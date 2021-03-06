package com.hfad.tagalong.track_network.model

import com.google.gson.annotations.SerializedName
import com.hfad.tagalong.network_core.model.ImageDto

data class AlbumDto (
    @SerializedName("album_type") var albumType : String,
    @SerializedName("artists") var artists : List<ArtistDto>,
    @SerializedName("href") var href : String,
    @SerializedName("id") var id : String,
    @SerializedName("images") var images : List<ImageDto>,
    @SerializedName("name") var name : String,
    @SerializedName("type") var type : String,
    @SerializedName("uri") var uri : String
)
