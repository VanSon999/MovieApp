package vanson.dev.movieapp.data.models.movie


import com.google.gson.annotations.SerializedName

data class Recommendations(
    @SerializedName("results")
    val movies: List<Movie>
)