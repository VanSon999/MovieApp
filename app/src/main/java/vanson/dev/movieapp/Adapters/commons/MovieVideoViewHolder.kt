package vanson.dev.movieapp.Adapters.commons

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.codewaves.youtubethumbnailview.ThumbnailLoadingListener
import kotlinx.android.synthetic.main.video_item_layout.view.*

class MovieVideoViewHolder(itemView: View, private val activity: Activity) :
    RecyclerView.ViewHolder(itemView) {
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