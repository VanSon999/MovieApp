package vanson.dev.movieapp.view_model.popular_top_playing

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_movies.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.adapter.MoviePagedListAdapter
import vanson.dev.movieapp.data.api.TheMovieDBClient
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.data.repository.TypeMovie

class MoviesActivity : AppCompatActivity() {
    private lateinit var mViewModel: MoviePageViewModel
    private lateinit var mRepository: MoviePagedListRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val typeMovie: TypeMovie = intent.getSerializableExtra("type_list") as TypeMovie
        val apiService = TheMovieDBClient.getClient()

        mRepository = MoviePagedListRepository(apiService, typeMovie)
        mViewModel = getViewModel()

        val movieAdapter = MoviePagedListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if(viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1
                return 3
            }
        }

        rv_movies.adapter = movieAdapter
        rv_movies.setHasFixedSize(true)
        rv_movies.layoutManager = gridLayoutManager

        mViewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        mViewModel.networkState.observe(this, Observer {
            progress_bar_movies.visibility = if(mViewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if(mViewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if(!mViewModel.listIsEmpty()){
                movieAdapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel():MoviePageViewModel{
        return ViewModelProvider(this, object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MoviePageViewModel(mRepository) as T
            }
        })[MoviePageViewModel::class.java]
    }
}