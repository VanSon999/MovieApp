package vanson.dev.movieapp.Interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import vanson.dev.movieapp.Models.MoviesResponse
import vanson.dev.movieapp.Models.PersonResponse

interface RetrofitService {

    @GET("search/movie")
    fun getMoviesByName(@Query("api_key") api_key: String, @Query("query") query: String):Call<MoviesResponse>

    @GET("search/person")
    fun getPersonByName(@Query("api_key") api_key: String, @Query("query") query: String):Call<PersonResponse>
}