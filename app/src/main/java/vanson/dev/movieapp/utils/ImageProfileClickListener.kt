package vanson.dev.movieapp.utils

import androidx.appcompat.widget.AppCompatImageView
import vanson.dev.movieapp.data.models.person.ProfileImage

interface ImageProfileClickListener {
    fun onImageProfileClick(imageProfile: ProfileImage, imageView: AppCompatImageView)
}