package vanson.dev.movieapp.view_model.detail

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_movie_detail.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.adapter.CastAdapter
import vanson.dev.movieapp.adapter.MovieAdapter
import vanson.dev.movieapp.data.models.movie.Cast
import vanson.dev.movieapp.data.models.movie.Movie
import vanson.dev.movieapp.data.models.movie.MovieDetails
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.utils.MovieItemClickListener
import vanson.dev.movieapp.utils.loadBackImage
import vanson.dev.movieapp.utils.loadPosterImage
import vanson.dev.movieapp.view_model.common.BaseActivity
import vanson.dev.movieapp.view_model.common.ImageViewerActivity
import vanson.dev.movieapp.view_model.person.PersonDetailActivity
import vanson.dev.movieapp.view_model.player.MoviePlayerActivity

class MovieDetailActivity : BaseActivity(), MovieItemClickListener {
    private lateinit var mViewModel: MovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository
    private lateinit var mCastAdapter: CastAdapter
    private lateinit var mRecommendAdapter: MovieAdapter
    private lateinit var mSimilarAdapter: MovieAdapter

    private lateinit var posterImage: String
    private lateinit var backDropImage: String

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        super.init(R.id.fragment_container_movie_detail, detail_movie_ui)
        //Init request client and parameter
        val movieId = intent.getIntExtra("id_movie", 271110)
        //val movieId = 43904
        movieRepository = MovieDetailsRepository(apiService)
        mViewModel = createViewModelFactory(movieId)

        setupAdapter()

        //observe data - bindUi
        mViewModel.movieDetails.observe(this, {
            bindUi(it)
        })

        mViewModel.networkState.observe(this, {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            if (it == NetworkState.ERROR) {
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

        detail_movie_img.setOnClickListener {
            val intent = Intent(this, ImageViewerActivity::class.java)
            intent.putExtra("url_image", posterImage)
            val options = ActivityOptions.makeSceneTransitionAnimation(this, it, "image_transition")
            startActivity(intent, options.toBundle())
        }

        detail_movie_cover.setOnClickListener {
            val intent = Intent(this, ImageViewerActivity::class.java)
            intent.putExtra("url_image", backDropImage)
            val options = ActivityOptions.makeSceneTransitionAnimation(this, it, "image_transition")
            startActivity(intent, options.toBundle())
        }
    }

    private fun setupAdapter() {
        //Setup Cast Adapter
        mCastAdapter = CastAdapter(this)
        rv_cast.adapter = mCastAdapter
        val layoutManagerCast = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManagerCast.isMeasurementCacheEnabled = false
        rv_cast.layoutManager = layoutManagerCast
        rv_cast.addOnScrollListener(object :
            RecyclerView.OnScrollListener() { //fix problem when item have different height
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                layoutManagerCast.requestLayout()
            }
        })
        //Setup Recommend Adapter
        mRecommendAdapter = MovieAdapter(this)
        rv_recommend.adapter = mRecommendAdapter
        val layoutManagerRecommend =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManagerRecommend.isMeasurementCacheEnabled = false
        rv_recommend.layoutManager = layoutManagerRecommend
        rv_recommend.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                layoutManagerRecommend.requestLayout()
            }
        })
        //Setup Similar Adapter
        mSimilarAdapter = MovieAdapter(this)
        rv_similar.adapter = mSimilarAdapter
        val layoutManagerSimilar = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManagerSimilar.isMeasurementCacheEnabled = false
        rv_similar.layoutManager = layoutManagerSimilar
        rv_similar.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                layoutManagerSimilar.requestLayout()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindUi(info: MovieDetails) {
        posterImage = info.posterPath
        backDropImage = info.backdropPath
        titleActivity.text = info.title
        detail_movie_title.text = info.title
        detail_movie_img.loadPosterImage(info.posterPath)
        detail_movie_cover.loadBackImage(info.backdropPath)
        detail_movie_desc.text = info.overview
        rating_movie.text = info.voteAverage.toString() + "/10"
        release_date.text = info.getReleaseDate()
        mCastAdapter.updateCast(info.casts.cast)
        mRecommendAdapter.updateMovie(info.recommendations.movies)
        mSimilarAdapter.updateMovie(info.similar.movies)
    }

    private fun createViewModelFactory(movieId: Int): MovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
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

    override fun onPlayClick(movie: Movie) {}
    override fun onMovieClickBackPost(movie: Movie, movieImage: ImageView) {}
    override fun onCastClick(cast: Cast, castImage: ImageView) {
        val intent = Intent(this, PersonDetailActivity::class.java)
        intent.putExtra("id_person", cast.id)
//        val options = ActivityOptions.makeSceneTransitionAnimation(this, castImage, "sharedName_2")
//        startActivity(intent, options.toBundle())
        startActivity(intent)
        overridePendingTransition(R.anim.slide_up, R.anim.no_change)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}