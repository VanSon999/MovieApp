package vanson.dev.movieapp.Adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.profile_image_layout.view.*
import vanson.dev.movieapp.ImageViewerActivity
import vanson.dev.movieapp.Models.ProfileImage
import vanson.dev.movieapp.R
import vanson.dev.movieapp.Utils.loadProfileImage

class ProfileImageAdapter(val activity: Activity, data: List<ProfileImage>) : RecyclerView.Adapter<ProfileImageAdapter.ViewHolder>() {
    private val mData : List<ProfileImage> = data

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        init {
            itemView.image_profile.setOnClickListener {
                val intent = Intent(activity, ImageViewerActivity::class.java)
                intent.putExtra("url_image", mData[adapterPosition].filePath)
                val options = ActivityOptions.makeSceneTransitionAnimation(activity, it, "image_transition")
                activity.startActivity(intent, options.toBundle())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_image_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            image_profile.loadProfileImage(mData[position].filePath)
        }
    }

    override fun getItemCount(): Int = mData.size
}