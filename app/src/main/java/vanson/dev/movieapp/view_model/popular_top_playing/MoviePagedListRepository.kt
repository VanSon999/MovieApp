package vanson.dev.movieapp.view_model.popular_top_playing

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.api.POST_PER_PAGE
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.movie.Movie
import vanson.dev.movieapp.data.repository.movie_data_paging.MovieDataSourcePaging
import vanson.dev.movieapp.data.repository.movie_data_paging.MovieDataSourcePagingFactory
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.data.repository.TypeMovie

class MoviePagedListRepository(private val apiService: TheMovieDBInterface, private val type_: TypeMovie) {
    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourcePagingFactory: MovieDataSourcePagingFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>>{
        moviesDataSourcePagingFactory = when(type_){
            TypeMovie.POPULAR -> MovieDataSourcePagingFactory(apiService::getPopularMovie, compositeDisposable)
            TypeMovie.TOP_RATED -> MovieDataSourcePagingFactory(apiService::getTopMovie, compositeDisposable)
            TypeMovie.NOW_PLAYING -> MovieDataSourcePagingFactory(apiService::getNewMovie, compositeDisposable)
        }

        val config = PagedList.Config.Builder() //setting chunk data of per page from each fetch from api of datasource
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourcePagingFactory, config).build()
        return moviePagedList
    }

    fun getNetworkState():LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSourcePaging, NetworkState>(
            moviesDataSourcePagingFactory.moviesLiveDataSource, MovieDataSourcePaging::networkState //get property networkState from MovieDataSourcePaging
        )
    }
}