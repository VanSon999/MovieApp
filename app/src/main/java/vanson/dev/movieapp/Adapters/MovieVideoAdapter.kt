package vanson.dev.movieapp.Adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.codewaves.youtubethumbnailview.ThumbnailLoader
import com.codewaves.youtubethumbnailview.ThumbnailLoadingListener
import kotlinx.android.synthetic.main.video_item_layout.view.*
import vanson.dev.movieapp.BuildConfig
import vanson.dev.movieapp.Models.Video
import vanson.dev.movieapp.R
import vanson.dev.movieapp.VideoPlayActivity

class MovieVideoAdapter(val activity: Activity, val data: List<Video>) :
    RecyclerView.Adapter<MovieVideoAdapter.ViewHolder>() {
    init {
        ThumbnailLoader.initialize(BuildConfig.YOUTUBE_KEY)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setThumbnailView(url: String?) {
            if (url != null && url.isNotEmpty()) {
                itemView.video_image_view.loadThumbnail(url, object : ThumbnailLoadingListener {
                    override fun onLoadingStarted(url: String, view: View) {
                        Log.i("TAG", "Thumbnail load started.");
                    }

                    override fun onLoadingComplete(url: String, view: View) {
                        Log.i("TAG", "Thumbnail load started.");
                    }

                    override fun onLoadingCanceled(url: String, view: View) {
                        Log.i("TAG", "Thumbnail load started.");
                    }

                    override fun onLoadingFailed(url: String, view: View, error: Throwable?) {
                        Toast.makeText(activity, error?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.video_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setThumbnailView("https://www.youtube.com/watch?v=" + data[position].key)
        with(holder.itemView) {
            video_title.text = data[position].name
            setOnClickListener { view ->
                val intent = Intent(activity, VideoPlayActivity::class.java)
                intent.putExtra("current_video", data[position])
                intent.putParcelableArrayListExtra(
                    "videos",
                    ArrayList(data.filter { it.key != data[position].key })
                )
                val options =
                    ActivityOptions.makeSceneTransitionAnimation(activity, view, "video_transition")
                activity.startActivity(intent, options.toBundle())
            }
        }
    }

    override fun getItemCount(): Int = data.size
}