package vanson.dev.movieapp.Models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("iso_3166_1")
    val iso31661: String = "",
    @SerializedName("iso_639_1")
    val iso6391: String = "",
    @SerializedName("key")
    val key: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("site")
    val site: String = "",
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("type")
    val type: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(iso31661)
        parcel.writeString(iso6391)
        parcel.writeString(key)
        parcel.writeString(name)
        parcel.writeString(site)
        parcel.writeInt(size)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Video> {
        override fun createFromParcel(parcel: Parcel): Video {
            return Video(parcel)
        }

        override fun newArray(size: Int): Array<Video?> {
            return arrayOfNulls(size)
        }
    }
}