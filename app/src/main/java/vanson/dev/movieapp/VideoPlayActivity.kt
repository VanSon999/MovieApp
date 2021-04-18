package vanson.dev.movieapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewaves.youtubethumbnailview.ThumbnailLoader
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import kotlinx.android.synthetic.main.activity_video_play.*
import vanson.dev.movieapp.Adapters.ExtraMovieVideoAdapter
import vanson.dev.movieapp.Models.Video
import vanson.dev.movieapp.Utils.FullScreenHelper

@Suppress("DEPRECATION")
class VideoPlayActivity : AppCompatActivity(), ExtraMovieVideoAdapter.Listener {
    private lateinit var fullScreenHelper: FullScreenHelper
    private lateinit var mYoutubePlayer: YouTubePlayer
    private lateinit var mAdapter: ExtraMovieVideoAdapter
    private var mVideos: ArrayList<Video>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)

        ThumbnailLoader.initialize(BuildConfig.YOUTUBE_KEY)
        fullScreenHelper = FullScreenHelper(this)

        progress_bar.indeterminateDrawable.setColorFilter(
            0xFFFFFFFF.toInt(),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
        val current = intent.getIntExtra("current_video", -1)
        mVideos = intent.getParcelableArrayListExtra<Video>("videos")

        if (current == -1 || mVideos.isNullOrEmpty()) {
            Toast.makeText(this, "No videos for this movie!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            play_video_title.text = mVideos!![current].name
            video_thumbnailview.loadThumbnail("https://www.youtube.com/watch?v=" + mVideos!![current].key)
            video_player_view.enableAutomaticInitialization = false
            video_player_view.initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    mYoutubePlayer = youTubePlayer
                    //hide thumbnail and progress bar when video ready to play
                    video_thumbnailview.visibility = View.GONE
                    progress_bar.visibility = View.GONE
                    video_player_view.visibility = View.VISIBLE
                    if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                        youTubePlayer.loadVideo(mVideos!![current].key, 0F)
                    } else {
                        youTubePlayer.cueVideo(mVideos!![current].key, 0F)
                    }
                }
            }, true)
            video_player_view.addFullScreenListener(object : YouTubePlayerFullScreenListener {
                override fun onYouTubePlayerEnterFullScreen() {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    fullScreenHelper.enterFullScreen()
                    isFullScreen = true
                }

                override fun onYouTubePlayerExitFullScreen() {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    fullScreenHelper.exitFullScreen()
                    isFullScreen = false
                }
            })
            if (isFullScreen) video_player_view.enterFullScreen()

            // recycler others
            if (mVideos!!.size <= 1) {
                other_videos_recycler.visibility = View.GONE
                no_result_found.visibility = View.VISIBLE
            } else {
                other_videos_recycler.visibility = View.VISIBLE
                no_result_found.visibility = View.GONE
                other_videos_recycler.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                mAdapter = ExtraMovieVideoAdapter(this, this)
                other_videos_recycler.adapter = mAdapter
                mAdapter.updateData(mVideos!!.filter { it.id != mVideos!![current].id })
                other_videos_recycler.layoutAnimation =
                    AnimationUtils.loadLayoutAnimation(this, R.anim.layout_slide_bottom)
                other_videos_recycler.scheduleLayoutAnimation()
            }
        }
    }

    override fun onBackPressed() {
        if (isFullScreen) {
            video_player_view.exitFullScreen()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private var isFullScreen = false
    }

    override fun changeVideoPlay(video: Video) {
        video_thumbnailview.loadThumbnail("https://www.youtube.com/watch?v=" + video.key)
        if (lifecycle.currentState == Lifecycle.State.RESUMED) {
            mYoutubePlayer.loadVideo(video.key, 0F)
        } else {
            mYoutubePlayer.cueVideo(video.key, 0F)
        }
        val newData = mVideos!!.filter { it.id != video.id }
        mAdapter.updateData(newData)
    }
}