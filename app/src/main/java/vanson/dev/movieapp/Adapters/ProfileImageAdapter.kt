package vanson.dev.movieapp.Adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.profile_image_layout.view.*
import vanson.dev.movieapp.Adapters.commons.ImagesViewHolder
import vanson.dev.movieapp.Models.ProfileImage
import vanson.dev.movieapp.R
import vanson.dev.movieapp.ScreenSlidePagerImageActivity
import vanson.dev.movieapp.Utils.loadProfileImage

class ProfileImageAdapter(val activity: Activity, data: List<ProfileImage>) : RecyclerView.Adapter<ImagesViewHolder>() {
    private val mData : List<ProfileImage> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_image_layout, parent, false)
        return ImagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        with(holder.itemView){
            image_profile.loadProfileImage(mData[position].filePath)
            image_profile.setOnClickListener {
                val images = mData.map { it -> it.filePath }
                val intent = Intent(activity, ScreenSlidePagerImageActivity::class.java)
                intent.putExtra("current_position", position)
                intent.putExtra("images", images.toTypedArray())
                val options = ActivityOptions.makeSceneTransitionAnimation(activity, it, "image_transition")
                activity.startActivity(intent, options.toBundle())
            }
        }
    }

    override fun getItemCount(): Int = mData.size
}