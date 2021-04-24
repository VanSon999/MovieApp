package vanson.dev.movieapp.view_model.common

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.movie.MovieResponse
import vanson.dev.movieapp.data.models.person.PersonResponse
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.data.repository.SearchNetworkDataSource

class SearchRepository(private val apiService: TheMovieDBInterface) {
    lateinit var searchNetworkDataSource: SearchNetworkDataSource

    fun init(compositeDisposable: CompositeDisposable) {
        searchNetworkDataSource = SearchNetworkDataSource(apiService, compositeDisposable)
    }

    fun searchMovies(name: String): LiveData<MovieResponse> {
        searchNetworkDataSource.searchMovies(name)
        return searchNetworkDataSource.responseMovies
    }

    fun searchPeople(name: String): LiveData<PersonResponse> {
        searchNetworkDataSource.searchPeople(name)
        return searchNetworkDataSource.responsePersons
    }

    fun getSearchNetworkState(): LiveData<NetworkState> {
        return searchNetworkDataSource.networkState
    }
}