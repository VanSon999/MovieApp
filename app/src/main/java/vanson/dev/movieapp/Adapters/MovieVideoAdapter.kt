package vanson.dev.movieapp.Adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codewaves.youtubethumbnailview.ThumbnailLoader
import kotlinx.android.synthetic.main.video_item_layout.view.*
import vanson.dev.movieapp.Adapters.commons.MovieVideoViewHolder
import vanson.dev.movieapp.BuildConfig
import vanson.dev.movieapp.Models.Video
import vanson.dev.movieapp.R
import vanson.dev.movieapp.VideoPlayActivity

class MovieVideoAdapter(val activity: Activity, val data: List<Video>) :
    RecyclerView.Adapter<MovieVideoViewHolder>() {
    init {
        ThumbnailLoader.initialize(BuildConfig.YOUTUBE_KEY)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVideoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.video_item_layout, parent, false)
        return MovieVideoViewHolder(view, activity)
    }

    override fun onBindViewHolder(holder: MovieVideoViewHolder, position: Int) {
        holder.setThumbnailView("https://www.youtube.com/watch?v=" + data[position].key)
        with(holder.itemView) {
            video_title.text = data[position].name
            setOnClickListener { view ->
                val intent = Intent(activity, VideoPlayActivity::class.java)
                intent.putExtra("current_video", position)
                intent.putParcelableArrayListExtra(
                    "videos",
                    ArrayList(data)
                )
                val options =
                    ActivityOptions.makeSceneTransitionAnimation(activity, view, "video_transition")
                activity.startActivity(intent, options.toBundle())
            }
        }
    }

    override fun getItemCount(): Int = data.size
}