package vanson.dev.movieapp

import android.widget.ImageView

interface MovieItemClickListener {
    fun onMovieClick(movie: Movie, movieImage: ImageView) //need imageview to share animation between two activity
}