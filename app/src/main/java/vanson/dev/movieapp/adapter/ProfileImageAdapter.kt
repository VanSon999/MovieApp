package vanson.dev.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.profile_image_item.view.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.data.models.person.ProfileImage
import vanson.dev.movieapp.utils.DiffCallBack
import vanson.dev.movieapp.utils.ImageProfileClickListener
import vanson.dev.movieapp.utils.loadProfileImage

class ProfileImageAdapter(val listener: ImageProfileClickListener) : RecyclerView.Adapter<ProfileImageAdapter.ViewHolder>() {
    private var mData : List<ProfileImage> = listOf()

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        init {
            itemView.setOnClickListener {
                listener.onImageProfileClick(mData[adapterPosition], it.image_profile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            image_profile.loadProfileImage(mData[position].filePath)
        }
    }

    override fun getItemCount(): Int = mData.size

    fun updateProfileImages(newData: List<ProfileImage>){
        val diffResult = DiffUtil.calculateDiff(DiffCallBack(mData, newData){it.filePath})
        mData = newData
        diffResult.dispatchUpdatesTo(this)
    }
}