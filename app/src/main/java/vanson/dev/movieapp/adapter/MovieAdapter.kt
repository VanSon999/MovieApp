package vanson.dev.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*
import vanson.dev.movieapp.utils.MovieItemClickListener
import vanson.dev.movieapp.R
import vanson.dev.movieapp.models.Movie

class MovieAdapter(private val mData: List<Movie>, private val listener: MovieItemClickListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
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
            item_movie_img.setImageResource(mData[position].getThumbnail())
            item_movie_title.text = mData[position].getTitle()
        }
    }

    override fun getItemCount() = mData.size
}