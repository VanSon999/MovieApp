package vanson.dev.movieapp.utils

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.flaviofaria.kenburnsview.KenBurnsView
import vanson.dev.movieapp.R

private fun View.ifNotDestroyed(block: () -> Unit) {
    if (!(context as Activity).isDestroyed) {
        block()
    }
}

fun ImageView.loadPhotoImage(url: String) = ifNotDestroyed {
    Glide.with(this).load(url).placeholder(
        R.drawable.default_avatar
    ).into(this)
}

fun ImageView.loadPosterImage(url: String) = ifNotDestroyed {
    Glide.with(this).load(url).placeholder(
        R.drawable.no_image
    ).into(this)
}

fun ImageView.loadBackImage(url: String) = ifNotDestroyed {
    Glide.with(this).load(url).placeholder(
        R.drawable.sunset
    ).into(this)
}

fun KenBurnsView.loadPersonImage(url: String?) = ifNotDestroyed {
    Glide.with(this).load("https://image.tmdb.org/t/p/w500$url").placeholder(
        R.drawable.default_avatar
    ).into(this)
}
