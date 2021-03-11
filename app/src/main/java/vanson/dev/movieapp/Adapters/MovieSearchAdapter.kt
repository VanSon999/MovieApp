package vanson.dev.movieapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_item.view.*
import vanson.dev.movieapp.Adapters.commons.SearchViewHolder
import vanson.dev.movieapp.BuildConfig.BASE_URL_IMAGE
import vanson.dev.movieapp.Models.Movie
import vanson.dev.movieapp.R

class MovieSearchAdapter(data: List<Movie>): RecyclerView.Adapter<SearchViewHolder>() {

    private val results = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.loadPoster(results[position].posterPath)
        holder.itemView.poster_title.text = results[position].title
    }

    override fun getItemCount(): Int = results.size
}