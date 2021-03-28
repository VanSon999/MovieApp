package vanson.dev.movieapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.person.PersonDetails

class PersonDetailNetworkDataSource (
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadMPersonDetailResponse = MutableLiveData<PersonDetails>()
    val downloadPersonDetailResponse: LiveData<PersonDetails>
        get() = _downloadMPersonDetailResponse

    fun fetchPersonDetail(personId: Int) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getDetailPerson(personId)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _downloadMPersonDetailResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    }, {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("DetailPersonDataSource", it.message.toString())
                    })
            )
        } catch (e: Exception) {
            Log.e("DetailPersonDataSource", e.message.toString())
        }
    }
}