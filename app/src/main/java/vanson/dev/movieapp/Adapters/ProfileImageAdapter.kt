package vanson.dev.movieapp.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.profile_image_layout.view.*
import vanson.dev.movieapp.Models.ProfileImage
import vanson.dev.movieapp.R
import vanson.dev.movieapp.Utils.loadProfileImage

class ProfileImageAdapter(val activity: Activity, data: List<ProfileImage>) : RecyclerView.Adapter<ProfileImageAdapter.ViewHolder>() {
    private val mData : List<ProfileImage> = data

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        init {
            itemView.setOnClickListener {
                //TO_DO
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