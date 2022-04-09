package com.hfad.tagalong.presentation.adapters

import android.os.Parcel
import android.os.Parcelable
import com.hfad.tagalong.playlist_domain.Playlist

data class PlaylistParcelable(
    val playlist: Playlist
) : Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.apply {
            writeString(playlist.id)
            writeString(playlist.name)
            writeInt(playlist.size)
            writeString(playlist.imageUrl)
        }
    }

    companion object CREATOR : Parcelable.Creator<Playlist> {
        override fun createFromParcel(parcel: Parcel): Playlist {
            return Playlist(
                id = parcel.readString()!!,
                name = parcel.readString()!!,
                size = parcel.readInt(),
                imageUrl = parcel.readString()
            )
        }

        override fun newArray(size: Int): Array<Playlist?> {
            return arrayOfNulls(size)
        }
    }

}