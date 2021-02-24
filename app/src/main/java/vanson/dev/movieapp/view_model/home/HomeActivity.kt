package vanson.dev.movieapp.view_model.home

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*
import vanson.dev.movieapp.*
import vanson.dev.movieapp.adapter.MovieAdapter
import vanson.dev.movieapp.adapter.SliderPagerAdapter
import vanson.dev.movieapp.data.api.TheMovieDBClient
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.movie_details.Movie
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.models.Slide
import vanson.dev.movieapp.utils.DataSource
import vanson.dev.movieapp.utils.MovieItemClickListener
import vanson.dev.movieapp.view_model.detail.MovieDetailActivity
import vanson.dev.movieapp.view_model.player.MoviePlayerActivity
import java.util.*

class HomeActivity : AppCompatActivity(), MovieItemClickListener {
    private lateinit var mViewModel: HomeViewModel
    private lateinit var mPopularAdapter: MovieAdapter
    private lateinit var mTopAdapter: MovieAdapter
    private lateinit var homeRepository: HomeRepository
    private lateinit var mAdapter: SliderPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        //init repository
        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        homeRepository = HomeRepository(apiService)

        //ViewModel
        mViewModel = createViewModelFactory()

        setupAdapter()

        mViewModel.homeMovies[0].observe(this, Observer {
            mAdapter.updateMovie(it.results.subList(0,5))
        })
        mViewModel.homeMovies[1].observe(this, Observer {
            mPopularAdapter.updateMovie(it.results)
        })

        mViewModel.homeMovies[2].observe(this, Observer {
            mTopAdapter.updateMovie(it.results)
        })

        mViewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            if(it == NetworkState.ERROR) Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
        })
    }

    private fun setupAdapter() {
        //Newest movie
        slide_pager.layoutParams.height = (resources.displayMetrics.widthPixels * 0.75).toInt()
        mAdapter = SliderPagerAdapter(this)
        slide_pager.adapter = mAdapter

        TabLayoutMediator(indicator, slide_pager) { tab, _ ->
            tab.select()
        }.attach()

        //setup timer to switch slides
        val timer = Timer()
        timer.scheduleAtFixedRate(SliderTimer(this, DataSource.getListSlide()), 4000, 6000)

        //Popular movie
        mPopularAdapter = MovieAdapter(this)
        rv_movies.adapter = mPopularAdapter
        rv_movies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //Top Rated movie
        mTopAdapter = MovieAdapter(this)
        rv_movies_week.adapter = mTopAdapter
        rv_movies_week.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun createViewModelFactory() =
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(homeRepository, 1) as T
            }
        })[HomeViewModel::class.java]

    class SliderTimer(private val activity: Activity, private val slides: List<Slide>) : TimerTask(){
        override fun run() {
            activity.runOnUiThread{
                if(activity.slide_pager.currentItem < slides.lastIndex){
                    activity.slide_pager.currentItem = activity.slide_pager.currentItem + 1
                }else{
                    activity.slide_pager.currentItem = 0
                }
            }
        }
    }

    override fun onMovieClick(movie: Movie, movieImage: ImageView) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("id_movie", movie.id)
        startActivity(intent)
    }

    override fun onPlayClick(movie: Movie) {
        val intent = Intent(this, MoviePlayerActivity::class.java)
        intent.putExtra("id_movie", movie.id)
        startActivity(intent)
    }
}