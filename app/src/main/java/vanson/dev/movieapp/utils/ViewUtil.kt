package vanson.dev.movieapp.utils

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
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
