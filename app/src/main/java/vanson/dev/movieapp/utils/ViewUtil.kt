package vanson.dev.movieapp.utils

import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.flaviofaria.kenburnsview.KenBurnsView
import com.jsibbold.zoomage.ZoomageView
import vanson.dev.movieapp.R
import vanson.dev.movieapp.data.api.BACK_BASE_URL
import vanson.dev.movieapp.data.api.POSTER_BASE_URL

private fun View.ifNotDestroyed(block: () -> Unit) {
    if (!(context as Activity).isDestroyed) {
        block()
    }
}

fun ImageView.loadPhotoImage(url: String) = ifNotDestroyed {
    Glide.with(this).load("$POSTER_BASE_URL$url").placeholder(
        R.drawable.default_avatar
    ).into(this)
}

fun ImageView.loadPosterImage(url: String) = ifNotDestroyed {
    Glide.with(this).load("$POSTER_BASE_URL$url").placeholder(
        R.drawable.no_image
    ).into(this)
}

fun ImageView.loadBackImage(url: String) = ifNotDestroyed {
    Glide.with(this).load("$BACK_BASE_URL$url").placeholder(
        R.drawable.sunset
    ).into(this)
}

fun KenBurnsView.loadPersonImage(url: String) = ifNotDestroyed {
    Glide.with(this).load("$BACK_BASE_URL$url").placeholder(
        R.drawable.default_avatar
    ).into(this)
}

fun KenBurnsView.loadBackImage(url: String) = ifNotDestroyed {
    Glide.with(this).load("$BACK_BASE_URL$url").placeholder(
        R.drawable.no_image
    ).into(this)
}

fun ZoomageView.loadImage(url: String) = ifNotDestroyed {
    Glide.with(this).load("$BACK_BASE_URL$url").placeholder(
        R.drawable.default_avatar
    ).into(this)
}

fun AppCompatImageView.loadProfileImage(url: String) = ifNotDestroyed {
    Glide.with(this).load("$POSTER_BASE_URL$url").placeholder(
        R.drawable.default_avatar
    ).into(this)
}
