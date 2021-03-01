package vanson.dev.movieapp.data.models.movie_details


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("release_date")
    val releaseDate: String = ""
)