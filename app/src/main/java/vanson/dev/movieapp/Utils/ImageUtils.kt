package vanson.dev.movieapp.Utils

import androidx.appcompat.widget.AppCompatImageView
import com.flaviofaria.kenburnsview.KenBurnsView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import vanson.dev.movieapp.BuildConfig
import vanson.dev.movieapp.R

fun KenBurnsView.loadPersonImage(uri: String?) {
    Picasso.get().load(BuildConfig.BASE_URL_SEARCH + uri).placeholder(R.drawable.no_image)
        .into(this)
}

fun KenBurnsView.loadBackGroundImage(uri: String?) {
    Picasso.get().load(BuildConfig.BASE_URL_SEARCH + uri).placeholder(R.drawable.no_image)
        .into(this)
}

fun AppCompatImageView.loadProfileImage(uri: String?) {
    Picasso.get().load(BuildConfig.BASE_URL_SEARCH + uri).placeholder(R.drawable.no_image)
        .into(this)
}

fun CircleImageView.loadProfileImage(uri: String?) {
    Picasso.get().load(BuildConfig.BASE_URL_SEARCH + uri).placeholder(R.drawable.no_image)
        .into(this)
}