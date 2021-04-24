package vanson.dev.movieapp.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.profile_image_item.view.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.data.models.movie.BackdropAndPoster
import vanson.dev.movieapp.utils.DiffCallBack
import vanson.dev.movieapp.utils.loadProfileImage
import vanson.dev.movieapp.view_model.common.ScreenSlidePagerImageActivity

class BackdropPosterMovieAdapter(val activity: Activity) :
    RecyclerView.Adapter<BackdropPosterMovieAdapter.ViewHolder>() {
    private var mData: List<BackdropAndPoster> = listOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.profile_image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            image_profile.loadProfileImage(mData[position].filePath)
            image_profile.setOnClickListener { view ->
                val images = mData.map { it.filePath }
                val intent = Intent(activity, ScreenSlidePagerImageActivity::class.java)
                intent.putExtra("current_position", position)
                intent.putExtra("images", images.toTypedArray())
                val options =
                    ActivityOptions.makeSceneTransitionAnimation(activity, view, "image_transition")
                activity.startActivity(intent, options.toBundle())
            }
        }
    }

    override fun getItemCount(): Int = mData.size

    fun updateData(newData: List<BackdropAndPoster>) {
        val diffUtil = DiffUtil.calculateDiff(DiffCallBack(mData, newData) { it.filePath })
        mData = newData
        diffUtil.dispatchUpdatesTo(this)
    }
}