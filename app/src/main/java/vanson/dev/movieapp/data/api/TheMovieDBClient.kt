package vanson.dev.movieapp.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "48f9067d74482e05b9bb40c806cb24f0"
const val YOUTUBE_KEY = "AIzaSyBwZI-Wlh-Pe-CCmega9FD4JdiRJeCZgho"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val FIRST_PAGE = 1
const val POST_PER_PAGE = 20
const val BACK_BASE_URL = "https://image.tmdb.org/t/p/original"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

object TheMovieDBClient {
    fun getClient(): TheMovieDBInterface {
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBInterface::class.java)
    }
}