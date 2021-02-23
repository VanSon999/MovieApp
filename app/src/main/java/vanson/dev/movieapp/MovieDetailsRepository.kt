package vanson.dev.movieapp

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.repository.MovieDetailsNetworkDataSource
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.data.vo.MovieDetais

class MovieDetailsRepository(private val apiService: TheMovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetais>{
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadMovieDetailsResponse
    }

    fun getMovieDetailNetworkState(): LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }
}