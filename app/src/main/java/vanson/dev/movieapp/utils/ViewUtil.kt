package vanson.dev.movieapp.utils

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

private fun View.ifNotDestroyed(block: () -> Unit) {
    if (!(context as Activity).isDestroyed) {
        block()
    }
}

fun ImageView.loadImage(url: String) = ifNotDestroyed { Glide.with(this).load(url).into(this) }
