package vanson.dev.movieapp.Models


import com.google.gson.annotations.SerializedName

data class PersonImages(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("profiles")
    val profileImages: List<ProfileImage> = listOf()
)