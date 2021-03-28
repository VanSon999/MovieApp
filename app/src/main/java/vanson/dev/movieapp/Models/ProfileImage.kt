package vanson.dev.movieapp.Models


import com.google.gson.annotations.SerializedName

data class ProfileImage(
    @SerializedName("aspect_ratio")
    val aspectRatio: Double = 0.0,
    @SerializedName("file_path")
    val filePath: String = "",
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("iso_639_1")
    val iso6391: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int = 0,
    @SerializedName("width")
    val width: Int = 0
)