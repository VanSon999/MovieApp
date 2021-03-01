package vanson.dev.movieapp.data.repository.moviedatapaging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import vanson.dev.movieapp.data.models.movie_details.Movie
import vanson.dev.movieapp.data.models.new_popular_top.Movies

class MovieDataSourcePagingFactory(private val funApiService: (Int) -> Single<Movies>, private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, Movie>() {
    val moviesLiveDataSource = MutableLiveData<MovieDataSourcePaging>()

    override fun create(): DataSource<Int, Movie> {
        val moviesDataSource = MovieDataSourcePaging(funApiService, compositeDisposable)
        moviesLiveDataSource.postValue(moviesDataSource)
        return moviesDataSource
    }
}