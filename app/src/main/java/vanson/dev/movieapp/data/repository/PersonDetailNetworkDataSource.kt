package vanson.dev.movieapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.person.PersonDetails
import vanson.dev.movieapp.data.models.person.PersonImages

class PersonDetailNetworkDataSource (
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadMPersonDetailResponse = MutableLiveData<PersonDetails>()
    private val _downloadMPersonProfileImages = MutableLiveData<PersonImages>()

    val downloadPersonDetailResponse: LiveData<PersonDetails>
        get() = _downloadMPersonDetailResponse

    val downloadMPersonProfileImages: LiveData<PersonImages>
        get() = _downloadMPersonProfileImages

    fun fetchPersonDetail(personId: Int) {
        _networkState.postValue(NetworkState.LOADING)
        val checkComplete = ArrayList<Boolean>() //to handle multiple request with _netWorkState
        try {
            compositeDisposable.addAll(
                apiService.getDetailPerson(personId)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _downloadMPersonDetailResponse.postValue(it)
                        checkComplete.add(true)
                        if(checkComplete.size == 2) _networkState.postValue(NetworkState.LOADED)
                    }, {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("DetailPersonDataSource", it.message.toString())
                    }),
                apiService.getProfileImagesPerson(personId)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _downloadMPersonProfileImages.postValue(it)
                        checkComplete.add(true)
                        if(checkComplete.size == 2) _networkState.postValue(NetworkState.LOADED)
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