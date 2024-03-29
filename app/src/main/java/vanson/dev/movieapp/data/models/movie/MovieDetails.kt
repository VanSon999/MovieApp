package vanson.dev.movieapp.data.models.movie


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

data class MovieDetails(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("casts")
    val casts: Casts,
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("recommendations")
    val recommendations: Recommendations,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("similar")
    val similar: Similar,
    @SerializedName("title")
    val title: String,
    @SerializedName("images")
    val images: MovieImages = MovieImages(),
    @SerializedName("vote_average")
    val voteAverage: Double
){
    @SuppressLint("SimpleDateFormat")
    @JvmName("releaseDate")
    fun getReleaseDate(): String{
        if(releaseDate.isEmpty()) return "updating..."
        val date = SimpleDateFormat("yyyy-MM-dd").parse(this.releaseDate)
        return SimpleDateFormat("dd/MM/yyyy").format(date!!)
    }
}