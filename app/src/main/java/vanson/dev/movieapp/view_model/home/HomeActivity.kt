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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*
import vanson.dev.movieapp.*
import vanson.dev.movieapp.adapter.MovieAdapter
import vanson.dev.movieapp.adapter.SliderPagerAdapter
import vanson.dev.movieapp.data.api.TheMovieDBClient
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.movie_details.Movie
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.data.repository.TypeMovie
import vanson.dev.movieapp.models.Slide
import vanson.dev.movieapp.utils.DataSource
import vanson.dev.movieapp.utils.MovieItemClickListener
import vanson.dev.movieapp.view_model.detail.MovieDetailActivity
import vanson.dev.movieapp.view_model.player.MoviePlayerActivity
import vanson.dev.movieapp.view_model.popular_top_playing.MoviesActivity
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
        timer.scheduleAtFixedRate(SliderTimer(this, DataSource.getListSlide()), 4000, 6000)

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
                    see_more_1.visibility = View.VISIBLE
                }else{
                    see_more_1.visibility = View.GONE
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
        val options = ActivityOptions.makeSceneTransitionAnimation(this, movieImage, "sharedName")
        startActivity(intent, options.toBundle())
    }

    override fun onPlayClick(movie: Movie) {
        val intent = Intent(this, MoviePlayerActivity::class.java)
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