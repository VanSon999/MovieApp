package vanson.dev.movieapp.Models


import com.google.gson.annotations.SerializedName

data class CreditMovie(
    @SerializedName("cast")
    val cast: List<Cast> = listOf(),
    @SerializedName("crew")
    val crew: List<Crew> = listOf(),
    @SerializedName("id")
    val id: Int = 0
)