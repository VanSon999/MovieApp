package vanson.dev.movieapp.view_model.person

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.person.PersonDetails
import vanson.dev.movieapp.data.models.person.PersonImages
import vanson.dev.movieapp.data.models.person.ProfileImage
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.data.repository.PersonDetailNetworkDataSource

class PersonRepository(private val apiService: TheMovieDBInterface) {
    lateinit var personDetailNetworkDataSource: PersonDetailNetworkDataSource

    fun fetchSinglePersonDetail(compositeDisposable: CompositeDisposable, personId: Int): Pair<LiveData<PersonDetails>, LiveData<PersonImages>> {
        personDetailNetworkDataSource = PersonDetailNetworkDataSource(apiService, compositeDisposable)
        personDetailNetworkDataSource.fetchPersonDetail(personId)

        return Pair(personDetailNetworkDataSource.downloadPersonDetailResponse, personDetailNetworkDataSource.downloadMPersonProfileImages)
    }

    fun getMovieDetailNetworkState(): LiveData<NetworkState> {
        return personDetailNetworkDataSource.networkState
    }
}