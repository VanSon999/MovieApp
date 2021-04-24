package vanson.dev.movieapp.data.models.movie


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String = "",
    @SerializedName("title")
    val title: String = ""
)