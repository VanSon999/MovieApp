package vanson.dev.movieapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.movie.MovieResponse
import vanson.dev.movieapp.data.models.person.PersonResponse

class SearchNetworkDataSource(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _responseMovies = MutableLiveData<MovieResponse>()
    private val _responsePersons = MutableLiveData<PersonResponse>()

    val responseMovies: LiveData<MovieResponse>
        get() = _responseMovies

    val responsePersons: LiveData<PersonResponse>
        get() = _responsePersons

    fun searchMovies(name: String) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.searchMovieByName(name).subscribeOn(Schedulers.io()).subscribe(
                    {
                        _responseMovies.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    }, {
                        _networkState.postValue(NetworkState.ERROR)
                    }
                )
            )
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    fun searchPeople(name: String) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.searchPersonByName(name).subscribeOn(Schedulers.io()).subscribe(
                    {
                        _responsePersons.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    }, {
                        _networkState.postValue(NetworkState.ERROR)
                    }
                )
            )
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    companion object {
        const val TAG = "SearchNetworkDataSource"
    }
}