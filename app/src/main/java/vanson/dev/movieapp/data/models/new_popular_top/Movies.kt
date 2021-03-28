package vanson.dev.movieapp.data.models.new_popular_top


import com.google.gson.annotations.SerializedName
import vanson.dev.movieapp.data.models.movie.Movie

data class Movies(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<Movie> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)