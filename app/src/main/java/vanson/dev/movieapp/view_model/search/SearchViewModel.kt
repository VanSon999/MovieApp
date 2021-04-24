package vanson.dev.movieapp.view_model.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.models.movie.MovieResponse
import vanson.dev.movieapp.data.models.person.PersonResponse
import vanson.dev.movieapp.data.repository.NetworkState

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val movieSearchText = MutableLiveData<String>()
    private val personSearchText = MutableLiveData<String>()

    init {
        searchRepository.init(compositeDisposable)
    }

    val moviesResponse: LiveData<MovieResponse> =
        Transformations.switchMap(movieSearchText) { text ->
            searchRepository.searchMovies(text)
        }

    val peopleResponse: LiveData<PersonResponse> =
        Transformations.switchMap(personSearchText) { text ->
            searchRepository.searchPeople(text)
        }

    val networkState: LiveData<NetworkState> by lazy {
        searchRepository.getSearchNetworkState()
    }

    fun searchMovieByName(name: String) {
        movieSearchText.postValue(name)
    }

    fun searchPersonByName(name: String) {
        personSearchText.postValue(name)
    }

    override fun onCleared() { //will be call when activity or fragment destroy
        super.onCleared()
        compositeDisposable.dispose()
    }
}