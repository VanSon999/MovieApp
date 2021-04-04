package vanson.dev.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_movie_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vanson.dev.movieapp.Client.RetrofitClient
import vanson.dev.movieapp.Interfaces.RetrofitService
import vanson.dev.movieapp.Models.MovieDetail
import vanson.dev.movieapp.Utils.loadPersonImage
import vanson.dev.movieapp.Utils.loadProfileImage

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var mRetrofitService: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val idMovie = intent.getIntExtra("id_movie", -1)
        if (idMovie == -1) {
            Toast.makeText(this, "Please select movie you want to see!", Toast.LENGTH_SHORT).show()
            finish()
        }

        mRetrofitService = RetrofitClient.getClient().create(RetrofitService::class.java)
        val movieDetailCall = mRetrofitService.getMovieDetailById(idMovie, BuildConfig.API_KEY)
        movieDetailCall.enqueue(object : Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                val movieDetail = response.body()
                if (movieDetail != null) {
                    prepareMovieDetail(movieDetail)
                } else {
                    Toast.makeText(
                        this@MovieDetailActivity,
                        "Any details not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Toast.makeText(
                    this@MovieDetailActivity,
                    "Any details not found",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun prepareMovieDetail(movieDetail: MovieDetail) {
        movie_detail_profile_image_view.loadPersonImage(movieDetail.backdropPath)
        movie_detail_poster_circle_image.loadProfileImage(movieDetail.posterPath)
        val title = movieDetail.title
        val origin_title = movieDetail.originalTitle
        val origin_language = movieDetail.originalLanguage
        val adult_ = movieDetail.adult
        val status_ = movieDetail.status
        val runtime_ = movieDetail.runtime
        val budget_ = movieDetail.budget
        val revenue_ = movieDetail.revenue
        val genres_ = movieDetail.genres
        val productionCountries_ = movieDetail.productionCountries
        val releaseDate_ = movieDetail.releaseDate
        val overview_ = movieDetail.overview
        val homepage_ = movieDetail.homepage
        val rating = (movieDetail.voteAverage*10).toInt()

        movie_detail_title.text = title
        movie_detail_rating_bar.progress = rating

        if(origin_title.isNullOrEmpty()){
            original_title_layout.visibility = View.GONE
        }else{
            original_title_layout.visibility = View.VISIBLE
            original_title.text = origin_title
        }

        if(origin_language.isNullOrEmpty()){
            original_language_layout.visibility = View.GONE
        }else{
            original_language_layout.visibility = View.VISIBLE
            original_language.text = origin_language
        }

        if(adult_ == null){
            adult_layout.visibility = View.GONE
        }else{
            adult_layout.visibility = View.VISIBLE
            adult.text = adult_.toString()
        }

        if(status_.isNullOrEmpty()){
            status_layout.visibility = View.GONE
        }else{
            status_layout.visibility = View.VISIBLE
            status.text = status_
        }

        if(runtime_ == null){
            runtime_layout.visibility = View.GONE
        }else{
            runtime_layout.visibility = View.VISIBLE
            runtime.text = runtime_.toString()
        }

        if(budget_ == null){
            budget_layout.visibility = View.GONE
        }else{
            budget_layout.visibility = View.VISIBLE
            budget.text = "$$budget_"
        }

        if(revenue_ == null){
            revenue_layout.visibility = View.GONE
        }else{
            revenue_layout.visibility = View.VISIBLE
            revenue.text = "$$revenue_"
        }

        if(genres_.isEmpty()){
            genres_layout.visibility = View.GONE
        }else{
            genres_layout.visibility = View.VISIBLE
            genres.text = genres_.joinToString(", "){it.name}
        }

        if(productionCountries_.isEmpty()){
            production_country_layout.visibility = View.GONE
        }else{
            production_country_layout.visibility = View.VISIBLE
            production_country.text = productionCountries_.joinToString(", "){it.name}
        }

        if(releaseDate_.isNullOrEmpty()){
            release_date_layout.visibility = View.GONE
        }else{
            release_date_layout.visibility = View.VISIBLE
            release_date.text = releaseDate_
        }

        if(homepage_.isNullOrEmpty()){
            homepage_layout.visibility = View.GONE
        }else{
            homepage_layout.visibility = View.VISIBLE
            homepage.text = homepage_
        }

        if(overview_.isNullOrEmpty()){
            overview_layout.visibility = View.GONE
        }else{
            overview_layout.visibility = View.VISIBLE
            overview.text = overview_
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}