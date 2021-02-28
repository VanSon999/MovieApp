package vanson.dev.movieapp.view_model.popular_top_playing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.models.movie_details.Movie
import vanson.dev.movieapp.data.repository.NetworkState

class MoviePageViewModel(private val movieRepository: MoviePagedListRepository): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty():Boolean{
        return moviePagedList.value?.isEmpty() ?: true
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}