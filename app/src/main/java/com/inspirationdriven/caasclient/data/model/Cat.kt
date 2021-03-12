package com.inspirationdriven.caasclient.data.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Cat(
    @Json(name = "id") val id: String,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "tags") val tags: Array<String>
) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArray()!!
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cat

        if (id != other.id) return false
        if (createdAt != other.createdAt) return false
        if (!tags.contentEquals(other.tags)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + tags.contentHashCode()
        return result
    }

    fun getPreviewUrl(height: Int) =
        String.format(BASE_PREVIEW_URL, id, height)

    fun getUrl() =
        String.format(BASE_URL, id)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(createdAt)
        parcel.writeStringArray(tags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cat> {
        private const val BASE_URL = "https://cataas.com/cat/%s"
        private const val BASE_PREVIEW_URL = "$BASE_URL?height=%d}"

        override fun createFromParcel(parcel: Parcel): Cat {
            return Cat(parcel)
        }

        override fun newArray(size: Int): Array<Cat?> {
            return arrayOfNulls(size)
        }
    }
}