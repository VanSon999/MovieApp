package vanson.dev.movieapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import vanson.dev.movieapp.data.api.FIRST_PAGE
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.movie_details.Movie

class PopularMovieDataSource(private val apiService: TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, Movie>() {

    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        apiService.getPopularMovie(page)
            .subscribeOn(Schedulers.io())
            .subscribe({
                callback.onResult(it.results, null, page + 1)
                networkState.postValue(NetworkState.LOADED)
            },{
                networkState.postValue(NetworkState.ERROR)
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
        apiService.getPopularMovie(params.key)
            .subscribeOn(Schedulers.io())
            .subscribe({
                if(it.totalPages >= params.key){
                    callback.onResult(it.results, params.key + 1)
                    networkState.postValue(NetworkState.LOADED)
                }else{
                    networkState.postValue(NetworkState.ENDOFLIST)
                }
            },{
                networkState.postValue(NetworkState.ERROR)
            })
    }
}