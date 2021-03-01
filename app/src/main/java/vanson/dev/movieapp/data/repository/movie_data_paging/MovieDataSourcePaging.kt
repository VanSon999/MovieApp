package vanson.dev.movieapp.data.repository.movie_data_paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import vanson.dev.movieapp.data.api.FIRST_PAGE
import vanson.dev.movieapp.data.models.movie_details.Movie
import vanson.dev.movieapp.data.models.new_popular_top.Movies
import vanson.dev.movieapp.data.repository.NetworkState

class MovieDataSourcePaging(private val funApiService: (Int) -> Single<Movies>, private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, Movie>() {

    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    var last_page = 0
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        funApiService(page) //popular, top_rated, now_playing
            .subscribeOn(Schedulers.io())
            .subscribe({
                callback.onResult(it.results, null, page + 1)
                networkState.postValue(NetworkState.LOADED)
                last_page = it.totalPages
            },{
                networkState.postValue(NetworkState.ERROR)
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
        funApiService(params.key)
            .subscribeOn(Schedulers.io())
            .subscribe({
                if(it.totalPages >= params.key){
                    callback.onResult(it.results, params.key + 1)
                    networkState.postValue(NetworkState.LOADED)
                }else{
                    networkState.postValue(NetworkState.ENDOFLIST)
                }
            },{
                if(params.key > last_page){
                    networkState.postValue(NetworkState.ENDOFLIST)
                }else{
                    networkState.postValue(NetworkState.ERROR)
                }
            })
    }
}