package vanson.dev.movieapp.view_model.player

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.api.TheMovieDBClient
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.trailers.TrailersMovie
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.data.repository.TrailerMovieNetworkDataSource

class TrailersMovieRepository(private val apiService: TheMovieDBInterface) {
    lateinit var trailersMovieNetworkDataSource: TrailerMovieNetworkDataSource

    fun fetchTrailersMovie(compositeDisposable: CompositeDisposable, movieId: Int): LiveData<TrailersMovie>{
        trailersMovieNetworkDataSource = TrailerMovieNetworkDataSource(apiService, compositeDisposable)
        trailersMovieNetworkDataSource.fetchTrailersMovie(movieId)
        return trailersMovieNetworkDataSource.trailerMovieRespose
    }

    fun getTrailersMovieNetworkState(): LiveData<NetworkState>{
        return trailersMovieNetworkDataSource.networkState
    }
}