package com.hfad.tagalong.presentation.adapters

import android.os.Parcel
import android.os.Parcelable
import com.hfad.tagalong.tag_domain.Tag

data class TagParcelable(
    val tag: Tag
) : Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.apply {
            writeLong(tag.id)
            writeString(tag.name)
            writeInt(tag.size)
        }
    }

    companion object CREATOR : Parcelable.Creator<Tag> {
        override fun createFromParcel(parcel: Parcel): Tag {
            return Tag(
                id = parcel.readLong(),
                name = parcel.readString()!!,
                size = parcel.readInt()
            )
        }

        override fun newArray(size: Int): Array<Tag?> {
            return arrayOfNulls(size)
        }
    }

}