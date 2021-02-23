package vanson.dev.movieapp.data.models.trailers


import com.google.gson.annotations.SerializedName

data class TrailersMovie(
    @SerializedName("results")
    val trailers: List<Trailer>
)