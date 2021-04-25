package vanson.dev.movieapp.Models


import com.google.gson.annotations.SerializedName

data class MovieVideos(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("results")
    val videos: List<Video> = listOf()
)