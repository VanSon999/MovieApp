package vanson.dev.movieapp.data.vo


import com.google.gson.annotations.SerializedName

data class Recommendations(
    @SerializedName("results")
    val results: List<Result>
)