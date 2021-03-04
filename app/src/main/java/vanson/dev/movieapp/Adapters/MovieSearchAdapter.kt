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
import vanson.dev.movieapp.BuildConfig.BASE_URL_IMAGE
import vanson.dev.movieapp.Models.Movie
import vanson.dev.movieapp.R

class MovieSearchAdapter: RecyclerView.Adapter<MovieSearchAdapter.ViewHolder>() {

    private var results = listOf<Movie>()
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        init {
            val generator = RandomTransitionGenerator(1000, DecelerateInterpolator())
            itemView.diagonal_image.setTransitionGenerator(generator)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            diagonal_image.loadPoster(results[position].posterPath.toString())
            poster_title.text = results[position].title
        }
    }

    override fun getItemCount(): Int = results.size

}

fun ImageView.loadPoster(uri: String?){
    Picasso.get().load(BASE_URL_IMAGE + uri).into(this)
}