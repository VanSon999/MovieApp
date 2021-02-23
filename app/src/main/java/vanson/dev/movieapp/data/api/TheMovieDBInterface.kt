package vanson.dev.movieapp.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vanson.dev.movieapp.data.models.movie_details.MovieDetails
import vanson.dev.movieapp.data.models.trailers.TrailersMovie

interface TheMovieDBInterface {
    @GET("movie/{movie_id}?")
    fun getMovieDetails(@Path("movie_id") id: Int, @Query("append_to_response") moreInfo: String = "casts,similar,recommendations"): Single<MovieDetails>
    @GET("movie/{movie_id}/videos")
    fun getTrailerMovie(@Path("movie_id") id: Int): Single<TrailersMovie>
}