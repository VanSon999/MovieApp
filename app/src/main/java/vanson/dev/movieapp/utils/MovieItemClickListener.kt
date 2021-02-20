package vanson.dev.movieapp.utils

import android.widget.ImageView
import vanson.dev.movieapp.models.Movie

interface MovieItemClickListener {
    fun onMovieClick(movie: Movie, movieImage: ImageView) //need imageview to share animation between two activity
}