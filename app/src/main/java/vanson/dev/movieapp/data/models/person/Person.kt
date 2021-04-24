package vanson.dev.movieapp.data.models.person


import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("profile_path")
    val profilePath: String = ""
)