package vanson.dev.movieapp.view_model.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.models.new_popular_top.Movies
import vanson.dev.movieapp.data.repository.NetworkState

class HomeViewModel(private val homeRepository: HomeRepository, page: Int) :ViewModel(){
    private val compositeDisposable = CompositeDisposable()

    val homeMovies : Array<LiveData<Movies>> by lazy {
        homeRepository.fetchDataForHomeScreen(compositeDisposable, page)
    }

    val networkState: LiveData<NetworkState> by lazy {
        homeRepository.getHomeNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}