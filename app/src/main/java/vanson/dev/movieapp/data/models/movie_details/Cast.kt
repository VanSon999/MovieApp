package vanson.dev.movieapp.data.vo


import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("character")
    val character: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("profile_path")
    val profilePath: String?
)