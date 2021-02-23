package vanson.dev.movieapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*
import vanson.dev.movieapp.MovieDetailsRepository
import vanson.dev.movieapp.MovieViewModel
import vanson.dev.movieapp.R
import vanson.dev.movieapp.adapter.CastAdapter
import vanson.dev.movieapp.data.api.POSTER_BASE_URL
import vanson.dev.movieapp.data.api.TheMovieDBClient
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.data.vo.MovieDetails
import vanson.dev.movieapp.utils.DataSource

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var mViewModel: MovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository
    private lateinit var mCastAdapter: CastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        //Init request client and parameter
        val movieId = 271110
        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)
        mViewModel = createViewModelFactory(movieId)

        //observe data - bindUi
        mViewModel.movieDetails.observe(this, Observer {
            bindUi(it)
        })

        mViewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })


        //Setup Cast
        mCastAdapter = CastAdapter()
        rv_cast.adapter = mCastAdapter
        rv_cast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mCastAdapter.updateCast(DataSource.getCast()) //Need fix

        //setup Animation
        detail_movie_cover.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        play_fab.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        //Setup FloatingPointButton
        play_fab.setOnClickListener {
            val intent = Intent(this, MoviePlayerActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindUi(info: MovieDetails) {
        supportActionBar?.title = info.title
        detail_movie_title.text = info.title
        Glide.with(this).load(POSTER_BASE_URL + info.posterPath).placeholder(R.drawable.sunset).into(detail_movie_img)
        Glide.with(this).load(POSTER_BASE_URL + info.backdropPath).placeholder(R.drawable.sunset).into(detail_movie_cover)
        detail_movie_desc.text = info.overview
        rating_movie.text = info.voteAverage.toString() + "/10"
        release_date.text = info.getReleaseDate()
    }

    private fun createViewModelFactory(movieId: Int): MovieViewModel{
        return ViewModelProvider(this, object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieViewModel(movieRepository, movieId) as T
            }
        })[MovieViewModel::class.java]
    }
}