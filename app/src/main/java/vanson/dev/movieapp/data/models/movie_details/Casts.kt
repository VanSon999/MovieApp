package vanson.dev.movieapp.data.models.movie_details


import com.google.gson.annotations.SerializedName

data class Casts(
    @SerializedName("cast")
    val cast: List<Cast>
)