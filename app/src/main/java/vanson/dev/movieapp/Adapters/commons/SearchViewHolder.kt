package vanson.dev.movieapp.Adapters.commons

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_item.view.*
import vanson.dev.movieapp.BuildConfig
import vanson.dev.movieapp.PersonDetailActivity

class SearchViewHolder(view: View) :RecyclerView.ViewHolder(view) {
    init {
        val generator = RandomTransitionGenerator(1000, DecelerateInterpolator())
        itemView.diagonal_image.setTransitionGenerator(generator)
    }

    fun loadPoster(uri: String?){
        Picasso.get().load(BuildConfig.BASE_URL_SEARCH + uri).into(itemView.diagonal_image)
    }
}