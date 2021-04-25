package vanson.dev.movieapp.utils

import android.widget.ImageView
import vanson.dev.movieapp.data.models.person.ProfileImage

interface ImageProfileClickListener {
    fun onImageProfileClick(images: List<ProfileImage>, position: Int, imageView: ImageView)
}