package vanson.dev.movieapp.Models


import com.google.gson.annotations.SerializedName

data class ImagesMovie(
    @SerializedName("backdrops")
    val backdrops: List<BackdropAndPoster> = listOf(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("posters")
    val posters: List<BackdropAndPoster> = listOf()
)