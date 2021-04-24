package vanson.dev.movieapp.data.models.person


import com.google.gson.annotations.SerializedName

data class PersonResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val people: List<Person> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)