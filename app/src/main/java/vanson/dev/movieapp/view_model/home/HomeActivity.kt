package vanson.dev.movieapp.view_model.home

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.adapter.MovieAdapter
import vanson.dev.movieapp.adapter.SliderPagerAdapter
import vanson.dev.movieapp.data.models.movie.Movie
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.data.repository.TypeMovie
import vanson.dev.movieapp.utils.MovieItemClickListener
import vanson.dev.movieapp.view_model.common.BaseActivity
import vanson.dev.movieapp.view_model.detail.MovieDetailActivity
import vanson.dev.movieapp.view_model.popular_top_playing.MoviesActivity
import java.util.*

class HomeActivity : BaseActivity(), MovieItemClickListener {
    private lateinit var mViewModel: HomeViewModel
    private lateinit var mPopularAdapter: MovieAdapter
    private lateinit var mTopAdapter: MovieAdapter
    private lateinit var homeRepository: HomeRepository
    private lateinit var mAdapter: SliderPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        super.init(R.id.fragment_container_home, home_ui)
        backToHome.visibility = View.GONE

        //init repository
        homeRepository = HomeRepository(apiService)

        //ViewModel
        mViewModel = createViewModelFactory()

        setupAdapter()

        mViewModel.homeMovies[0].observe(this, {
            mAdapter.updateMovie(it.results.subList(0, 5))
        })
        mViewModel.homeMovies[1].observe(this, {
            mPopularAdapter.updateMovie(it.results)
        })

        mViewModel.homeMovies[2].observe(this, {
            mTopAdapter.updateMovie(it.results)
        })

        mViewModel.networkState.observe(this, {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            if (it == NetworkState.ERROR) Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
        })

        see_more_1.setOnClickListener {
            val intent = Intent(this, MoviesActivity::class.java)
            intent.putExtra("type_list", TypeMovie.POPULAR)
            startActivity(intent)
        }

        see_more_2.setOnClickListener {
            val intent = Intent(this, MoviesActivity::class.java)
            intent.putExtra("type_list", TypeMovie.TOP_RATED)
            startActivity(intent)
        }
    }

    private fun setupAdapter() {
        //Newest movie
        slide_pager.layoutParams.height = (resources.displayMetrics.widthPixels * 0.7).toInt()
        mAdapter = SliderPagerAdapter(this)
        slide_pager.adapter = mAdapter

        TabLayoutMediator(indicator, slide_pager) { tab, _ ->
            tab.select()
        }.attach()

        //setup timer to switch slides
        val timer = Timer()
        timer.scheduleAtFixedRate(SliderTimer(this, 5), 4000, 6000)

        //Popular movie
        mPopularAdapter = MovieAdapter(this)
        rv_movies.adapter = mPopularAdapter
        val layoutManagerPopular = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManagerPopular.isMeasurementCacheEnabled = false
        rv_movies.layoutManager = layoutManagerPopular
        rv_movies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                layoutManagerPopular.requestLayout() //fix size of each movies item
                val currentItem = layoutManagerPopular.findLastVisibleItemPosition()
                val size = layoutManagerPopular.itemCount
                if(currentItem == size - 1){ //last item
                    see_more_1.visibility = View.VISIBLE
                }else{
                    see_more_1.visibility = View.GONE
                }
            }
        })
        //Top Rated movie
        mTopAdapter = MovieAdapter(this)
        rv_movies_week.adapter = mTopAdapter
        val layoutManagerRated = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManagerRated.isMeasurementCacheEnabled = false
        rv_movies_week.layoutManager = layoutManagerRated
        rv_movies_week.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                layoutManagerRated.requestLayout()
                val currentItem = layoutManagerRated.findLastVisibleItemPosition()
                val size = layoutManagerRated.itemCount
                if(currentItem == size - 1){ //last item
                    see_more_2.visibility = View.VISIBLE
                }else{
                    see_more_2.visibility = View.GONE
                }
            }
        })
    }

    private fun createViewModelFactory() =
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(homeRepository, 1) as T
            }
        })[HomeViewModel::class.java]

    class SliderTimer(private val activity: Activity, private val numberSlide: Int) : TimerTask() {
        override fun run() {
            activity.runOnUiThread {
                if (activity.slide_pager.currentItem < numberSlide - 1) {
                    activity.slide_pager.currentItem = activity.slide_pager.currentItem + 1
                } else {
                    activity.slide_pager.currentItem = 0
                }
            }
        }
    }

    override fun onMovieClick(movie: Movie, movieImage: ImageView) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("id_movie", movie.id)
        val options = ActivityOptions.makeSceneTransitionAnimation(this, movieImage, "sharedName")
        startActivity(intent, options.toBundle())
    }

    override fun onPlayClick(movie: Movie) {
        val intent = Intent(this, Movie::class.java)
        intent.putExtra("id_movie", movie.id)
        startActivity(intent)
    }

    override fun onMovieClickBackPost(movie: Movie, movieImage: ImageView) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("id_movie", movie.id)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}