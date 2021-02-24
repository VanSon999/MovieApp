package vanson.dev.movieapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.new_popular_top.Movies
import java.lang.Exception

class HomeNetworkDataSource(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _popularMovies = MutableLiveData<Movies>()
    private val _topMovies = MutableLiveData<Movies>()
    private val _newestMovies = MutableLiveData<Movies>()

    val popularMovies: LiveData<Movies>
        get() = _popularMovies
    val topMovies: LiveData<Movies>
        get() = _topMovies
    val newestMovies: LiveData<Movies>
        get() = _newestMovies

    fun fetchMovies(page: Int) {
        _networkState.postValue(NetworkState.LOADING)
        val checkComplete = ArrayList<Boolean>() //to handle multiple request with _netWorkState
        try {
            compositeDisposable.addAll(
                apiService.getNewMovie(page)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _newestMovies.postValue(it)
                        checkComplete.add(true)
                        if(checkComplete.size == 3) _networkState.postValue(NetworkState.LOADED)
                    }, {
                        _networkState.postValue(NetworkState.ERROR)
                    }),
                apiService.getPopularMovie(page)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _popularMovies.postValue(it)
                        checkComplete.add(true)
                        if(checkComplete.size == 3) _networkState.postValue(NetworkState.LOADED)
                    }, {
                        _networkState.postValue(NetworkState.ERROR)
                    }),
                apiService.getTopMovie(page)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _topMovies.postValue(it)
                        checkComplete.add(true)
                        if(checkComplete.size == 3) _networkState.postValue(NetworkState.LOADED)
                    }, {
                        _networkState.postValue(NetworkState.ERROR)
                    })
            )
        } catch (e: Exception) {
            Log.e("HomeNetworkDataSource", e.message.toString())
        }
    }
}