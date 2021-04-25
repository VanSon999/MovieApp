package vanson.dev.movieapp.Client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val baseUrl = "http://api.themoviedb.org/3/"
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit{
        if(retrofit == null){
            retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }
}