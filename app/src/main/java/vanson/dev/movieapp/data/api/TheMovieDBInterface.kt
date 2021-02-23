package vanson.dev.movieapp.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vanson.dev.movieapp.data.vo.MovieDetais

interface TheMovieDBInterface {
    @GET("movie/{movie_id}?")
    fun getMovieDetails(@Path("movie_id") id: Int, @Query("append_to_response") moreInfo: String = "casts,similar,recommendations"): Single<MovieDetais>
}