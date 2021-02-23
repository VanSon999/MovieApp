package vanson.dev.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*
import vanson.dev.movieapp.utils.MovieItemClickListener
import vanson.dev.movieapp.R
import vanson.dev.movieapp.data.api.POSTER_BASE_URL
import vanson.dev.movieapp.data.vo.Movie
import vanson.dev.movieapp.utils.DiffCallBack
import vanson.dev.movieapp.utils.loadPosterImage

class MovieAdapter(private val listener: MovieItemClickListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var mData: List<Movie> = listOf()
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            this.view.setOnClickListener {
                listener.onMovieClick(mData[adapterPosition], this.view.item_movie_img)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view){
            item_movie_img.loadPosterImage(POSTER_BASE_URL + mData[position].posterPath)
            item_movie_title.text = mData[position].title
        }
    }

    override fun getItemCount() = mData.size

    fun updateMovie(newMovies: List<Movie>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallBack(mData,newMovies){it.id})
        mData = newMovies
        diffResult.dispatchUpdatesTo(this)
    }
}