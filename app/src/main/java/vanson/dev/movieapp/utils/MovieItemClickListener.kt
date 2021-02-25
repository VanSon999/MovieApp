package vanson.dev.movieapp.utils

import android.widget.ImageView
import vanson.dev.movieapp.data.models.movie_details.Movie

interface MovieItemClickListener {
    fun onMovieClick(movie: Movie, movieImage: ImageView) //need imageview to share animation between two activity
    fun onPlayClick(movie: Movie)
    fun onMovieClickBackPost(movie: Movie, movieImage: ImageView)
}