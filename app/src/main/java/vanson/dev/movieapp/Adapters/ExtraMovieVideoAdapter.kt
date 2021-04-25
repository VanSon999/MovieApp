package vanson.dev.movieapp.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codewaves.youtubethumbnailview.ThumbnailLoader
import kotlinx.android.synthetic.main.video_item_layout.view.*
import vanson.dev.movieapp.Adapters.commons.MovieVideoViewHolder
import vanson.dev.movieapp.BuildConfig
import vanson.dev.movieapp.Models.Video
import vanson.dev.movieapp.R
import vanson.dev.movieapp.Utils.DiffCallBack

class ExtraMovieVideoAdapter(val activity: Activity, val listener: Listener) :
    RecyclerView.Adapter<MovieVideoViewHolder>() {

    interface Listener {
        fun changeVideoPlay(video: Video)
    }

    private var mData = listOf<Video>()

    init {
        ThumbnailLoader.initialize(BuildConfig.YOUTUBE_KEY)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVideoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.video_item_layout, parent, false)
        return MovieVideoViewHolder(view, activity)
    }

    override fun onBindViewHolder(holder: MovieVideoViewHolder, position: Int) {
        holder.setThumbnailView("https://www.youtube.com/watch?v=" + mData[position].key)
        with(holder.itemView) {
            video_title.text = mData[position].name
            setOnClickListener {
                listener.changeVideoPlay(mData[position])
            }
        }
    }

    override fun getItemCount(): Int = mData.size

    fun updateData(newData: List<Video>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallBack(mData, newData) { it.id })
        mData = newData
        diffResult.dispatchUpdatesTo(this)
    }
}