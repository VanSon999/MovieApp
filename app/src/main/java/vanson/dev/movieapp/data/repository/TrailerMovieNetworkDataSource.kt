package vanson.dev.movieapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.trailers.TrailersMovie
import java.lang.Exception

class TrailerMovieNetworkDataSource(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _trailersMovieResponse = MutableLiveData<TrailersMovie>()
    val trailerMovieResponse: LiveData<TrailersMovie>
        get() = _trailersMovieResponse

    fun fetchTrailersMovie(movieId: Int){
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getTrailerMovie(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _trailersMovieResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },{
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("TrailersMovieDataSource", it.message.toString())
                    })
            )
        }catch (e: Exception){
            Log.e("TrailersMovieDataSource", e.message.toString())
        }
    }
}