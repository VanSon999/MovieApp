package vanson.dev.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.data.vo.MovieDetails

class MovieViewModel(private val movieRepository: MovieDetailsRepository, movieId: Int): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() { //will be call when activity or fragment destroy
        super.onCleared()
        compositeDisposable.dispose()
    }
}
