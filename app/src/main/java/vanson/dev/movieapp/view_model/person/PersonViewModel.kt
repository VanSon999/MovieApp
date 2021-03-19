package vanson.dev.movieapp.view_model.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.models.movie_details.PersonDetails
import vanson.dev.movieapp.data.repository.NetworkState

class PersonViewModel(private val personRepository: PersonRepository, personId: Int): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val personDetail: LiveData<PersonDetails> by lazy {
        personRepository.fetchSinglePersonDetail(compositeDisposable, personId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        personRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() { //will be call when activity or fragment destroy
        super.onCleared()
        compositeDisposable.dispose()
    }
}