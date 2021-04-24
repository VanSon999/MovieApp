package vanson.dev.movieapp.data.models.movie


import com.google.gson.annotations.SerializedName

data class MovieImages(
    @SerializedName("backdrops")
    val backdrops: List<BackdropAndPoster> = listOf(),
    @SerializedName("posters")
    val posters: List<BackdropAndPoster> = listOf()
)