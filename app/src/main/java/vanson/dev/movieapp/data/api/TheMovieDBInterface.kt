package vanson.dev.movieapp.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vanson.dev.movieapp.data.models.movie_details.MovieDetails
import vanson.dev.movieapp.data.models.movie_details.PersonDetails
import vanson.dev.movieapp.data.models.new_popular_top.Movies
import vanson.dev.movieapp.data.models.trailers.TrailersMovie

interface TheMovieDBInterface {
    @GET("movie/{movie_id}?")
    fun getMovieDetails(@Path("movie_id") id: Int, @Query("append_to_response") moreInfo: String = "casts,similar,recommendations"): Single<MovieDetails>
    @GET("movie/{movie_id}/videos")
    fun getTrailerMovie(@Path("movie_id") id: Int): Single<TrailersMovie>
    @GET("movie/popular?")
    fun getPopularMovie(@Query("page") page: Int = 1): Single<Movies>
    @GET("movie/top_rated?")
    fun getTopMovie(@Query("page") page: Int = 1): Single<Movies>
    @GET("movie/now_playing?")
    fun getNewMovie(@Query("page") page: Int = 1): Single<Movies>
    @GET("person/{person_id}")
    fun getDetailPerson(@Path("person_id") id: Int): Single<PersonDetails>
}