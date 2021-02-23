package vanson.dev.movieapp.ui

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_movie_detail.*
import vanson.dev.movieapp.MovieDetailsRepository
import vanson.dev.movieapp.MovieViewModel
import vanson.dev.movieapp.R
import vanson.dev.movieapp.adapter.CastAdapter
import vanson.dev.movieapp.adapter.MovieAdapter
import vanson.dev.movieapp.data.api.POSTER_BASE_URL
import vanson.dev.movieapp.data.api.TheMovieDBClient
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.data.models.movie_details.Movie
import vanson.dev.movieapp.data.models.movie_details.MovieDetails
import vanson.dev.movieapp.utils.MovieItemClickListener
import vanson.dev.movieapp.utils.loadBackImage
import vanson.dev.movieapp.utils.loadPosterImage

class MovieDetailActivity : AppCompatActivity(), MovieItemClickListener {
    private lateinit var mViewModel: MovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository
    private lateinit var mCastAdapter: CastAdapter
    private lateinit var mRecommendAdapter: MovieAdapter
    private lateinit var mSimilarAdapter: MovieAdapter

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        //Init request client and parameter
        val movieId = intent.getIntExtra("id_movie",271110)
        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)
        mViewModel = createViewModelFactory(movieId)

        setupAdapter()

        //observe data - bindUi
        mViewModel.movieDetails.observe(this, Observer {
            bindUi(it)
        })

        mViewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            if(it == NetworkState.ERROR){
                Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
            }
        })

        //setup Animation
        detail_movie_cover.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        play_fab.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        //Setup FloatingPointButton
        play_fab.setOnClickListener {
            val intent = Intent(this, MoviePlayerActivity::class.java)
            intent.putExtra("id_movie", movieId)
            startActivity(intent)
        }
    }

    private fun setupAdapter() {
        //Setup Cast Adapter
        mCastAdapter = CastAdapter()
        rv_cast.adapter = mCastAdapter
        rv_cast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //Setup Recommend Adapter
        mRecommendAdapter = MovieAdapter(this)
        rv_recommend.adapter = mRecommendAdapter
        rv_recommend.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //Setup Similar Adapter
        mSimilarAdapter = MovieAdapter(this)
        rv_similar.adapter = mSimilarAdapter
        rv_similar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    @SuppressLint("SetTextI18n")
    private fun bindUi(info: MovieDetails) {
        supportActionBar?.title = info.title
        detail_movie_title.text = info.title
//        Glide.with(this).load(POSTER_BASE_URL + info.posterPath).placeholder(R.drawable.no_image).into(detail_movie_img)
//        Glide.with(this).load(POSTER_BASE_URL + info.backdropPath).placeholder(R.drawable.sunset).into(detail_movie_cover)
        detail_movie_img.loadPosterImage(POSTER_BASE_URL + info.posterPath)
        detail_movie_cover.loadBackImage(POSTER_BASE_URL + info.backdropPath)
        detail_movie_desc.text = info.overview
        rating_movie.text = info.voteAverage.toString() + "/10"
        release_date.text = info.getReleaseDate()
        mCastAdapter.updateCast(info.casts.cast)
        mRecommendAdapter.updateMovie(info.recommendations.movies)
        mSimilarAdapter.updateMovie(info.similar.movies)
    }

    private fun createViewModelFactory(movieId: Int): MovieViewModel{
        return ViewModelProvider(this, object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieViewModel(movieRepository, movieId) as T
            }
        })[MovieViewModel::class.java]
    }

    override fun onMovieClick(movie: Movie, movieImage: ImageView) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("id_movie", movie.id)
        val options = ActivityOptions.makeSceneTransitionAnimation(this, movieImage, "sharedName")
        startActivity(intent, options.toBundle())
    }
}