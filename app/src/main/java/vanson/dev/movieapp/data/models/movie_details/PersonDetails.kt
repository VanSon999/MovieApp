package vanson.dev.movieapp.data.models.movie_details


import com.google.gson.annotations.SerializedName

data class PersonDetails(
    @SerializedName("adult")
    val adult: Boolean = false,
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String> = listOf(),
    @SerializedName("biography")
    val biography: String = "",
    @SerializedName("birthday")
    val birthday: String? = "",
    @SerializedName("deathday")
    val deathday: String? = null,
    @SerializedName("gender")
    val gender: Int = 0,
    @SerializedName("homepage")
    val homepage: String? = null,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("imdb_id")
    val imdbId: String = "",
    @SerializedName("known_for_department")
    val knownForDepartment: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("place_of_birth")
    val placeOfBirth: String? = "",
    @SerializedName("popularity")
    val popularity: Double = 0.0,
    @SerializedName("profile_path")
    val profilePath: String = ""
)