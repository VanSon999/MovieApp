package vanson.dev.movieapp.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_item.view.*
import vanson.dev.movieapp.Adapters.commons.SearchViewHolder
import vanson.dev.movieapp.Models.Movie
import vanson.dev.movieapp.MovieDetailActivity
import vanson.dev.movieapp.R

class MovieSearchAdapter(private val activity: Activity, data: List<Movie>): RecyclerView.Adapter<SearchViewHolder>() {

    private val results = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        with(holder){
            loadPoster(results[position].posterPath)
            itemView.poster_title.text = results[position].title
            itemView.setOnClickListener {
                val intent = Intent(activity, MovieDetailActivity::class.java)
                intent.putExtra("id_movie", results[position].id)
                activity.startActivity(intent)
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }

    override fun getItemCount(): Int = results.size
}