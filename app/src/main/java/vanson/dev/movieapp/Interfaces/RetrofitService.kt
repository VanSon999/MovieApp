package vanson.dev.movieapp.Interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vanson.dev.movieapp.Models.*

interface RetrofitService {

    @GET("search/movie")
    fun getMoviesByName(@Query("api_key") api_key: String, @Query("query") query: String):Call<MoviesResponse>

    @GET("search/person")
    fun getPersonByName(@Query("api_key") api_key: String, @Query("query") query: String):Call<PersonResponse>

    @GET("person/{person_id}")
    fun getPersonDetailsById(@Path("person_id") person_id: Int, @Query("api_key") api_key: String):Call<PersonDetails>

    @GET("person/{person_id}/images")
    fun getProfileImagesPerson(@Path("person_id") person_id: Int, @Query("api_key") api_key: String):Call<PersonImages>

    @GET("movie/{movie_id}")
    fun getMovieDetailById(@Path("movie_id") movie_id: Int, @Query("api_key") api_key: String):Call<MovieDetail>
}