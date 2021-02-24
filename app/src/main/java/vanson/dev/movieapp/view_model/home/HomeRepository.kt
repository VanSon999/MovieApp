package vanson.dev.movieapp.view_model.home

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.new_popular_top.Movies
import vanson.dev.movieapp.data.repository.HomeNetworkDataSource
import vanson.dev.movieapp.data.repository.NetworkState

class HomeRepository(private val apiService: TheMovieDBInterface) {
    lateinit var homeNetworkDataSource: HomeNetworkDataSource

    fun fetchDataForHomeScreen(compositeDisposable: CompositeDisposable, page: Int): Array<LiveData<Movies>>{
        homeNetworkDataSource = HomeNetworkDataSource(apiService, compositeDisposable)
        homeNetworkDataSource.fetchMovies(page)
        return arrayOf(homeNetworkDataSource.newestMovies, homeNetworkDataSource.popularMovies, homeNetworkDataSource.topMovies)
    }

    fun getHomeNetworkState():LiveData<NetworkState>{
        return homeNetworkDataSource.networkState
    }
}