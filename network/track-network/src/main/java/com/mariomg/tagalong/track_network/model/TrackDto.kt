package com.mariomg.tagalong.track_network.model

import com.google.gson.annotations.SerializedName

data class TrackDto (
    @SerializedName("album") var album : AlbumDto,
    @SerializedName("artists") var artists : List<ArtistDto>,
    @SerializedName("disc_number") var discNumber : Int,
    @SerializedName("duration_ms") var durationMs : Int,
    @SerializedName("explicit") var explicit : Boolean,
    @SerializedName("href") var href : String,
    @SerializedName("id") var id : String?,
    @SerializedName("name") var name : String,
    @SerializedName("popularity") var popularity : Int,
    @SerializedName("preview_url") var previewUrl : String?,
    @SerializedName("track_number") var trackNumber : Int,
    @SerializedName("type") var type : String,
    @SerializedName("uri") var uri : String
)
