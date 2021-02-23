package vanson.dev.movieapp.data.vo


import com.google.gson.annotations.SerializedName

data class Casts(
    @SerializedName("cast")
    val cast: List<Cast>
)