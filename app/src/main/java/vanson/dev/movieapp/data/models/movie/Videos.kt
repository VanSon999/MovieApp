package vanson.dev.movieapp.data.models.movie


import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results")
    val videos: List<Video> = listOf()
)