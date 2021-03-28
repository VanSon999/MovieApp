package vanson.dev.movieapp.Utils

import androidx.appcompat.widget.AppCompatImageView
import com.flaviofaria.kenburnsview.KenBurnsView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_item.view.*
import vanson.dev.movieapp.BuildConfig

fun KenBurnsView.loadPersonImage(uri: String?){
    Picasso.get().load(BuildConfig.BASE_URL_SEARCH + uri).into(this)
}

fun AppCompatImageView.loadProfileImage(uri: String?){
    Picasso.get().load(BuildConfig.BASE_URL_SEARCH + uri).into(this)
}