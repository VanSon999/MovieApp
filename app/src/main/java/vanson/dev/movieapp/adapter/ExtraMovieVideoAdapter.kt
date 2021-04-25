package vanson.dev.movieapp.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codewaves.youtubethumbnailview.ThumbnailLoader
import com.codewaves.youtubethumbnailview.ThumbnailLoadingListener
import kotlinx.android.synthetic.main.video_item_layout.view.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.data.api.YOUTUBE_KEY
import vanson.dev.movieapp.data.models.movie.Video
import vanson.dev.movieapp.utils.DiffCallBack

class ExtraMovieVideoAdapter(val activity: Activity, private val listener: Listener) :
    RecyclerView.Adapter<ExtraMovieVideoAdapter.MovieVideoViewHolder>() {

    interface Listener {
        fun changeVideoPlay(video: Video)
    }

    inner class MovieVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setThumbnailView(url: String?) {
            if (url != null && url.isNotEmpty()) {
                itemView.video_image_view.loadThumbnail(url, object : ThumbnailLoadingListener {
                    override fun onLoadingStarted(url: String, view: View) {
                        Log.i("TAG", "Thumbnail load started.")
                    }

                    override fun onLoadingComplete(url: String, view: View) {
                        Log.i("TAG", "Thumbnail load started.")
                    }

                    override fun onLoadingCanceled(url: String, view: View) {
                        Log.i("TAG", "Thumbnail load started.")
                    }

                    override fun onLoadingFailed(url: String, view: View, error: Throwable?) {
                        Toast.makeText(
                            activity,
                            error?.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
        }
    }

    private var mData = listOf<Video>()

    init {
        ThumbnailLoader.initialize(YOUTUBE_KEY)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVideoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.video_item_layout, parent, false)
        return MovieVideoViewHolder(view)
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