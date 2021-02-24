package vanson.dev.movieapp.view_model.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.models.trailers.TrailersMovie
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.view_model.player.TrailersMovieRepository

class TrailersViewModel(private val trailersMovieRepository: TrailersMovieRepository, movieId: Int) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val trailersMovie: LiveData<TrailersMovie> by lazy {
        trailersMovieRepository.fetchTrailersMovie(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        trailersMovieRepository.getTrailersMovieNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}