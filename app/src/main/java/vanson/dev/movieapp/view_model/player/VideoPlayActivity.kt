 package vanson.dev.movieapp.view_model.player

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
import vanson.dev.movieapp.R
import vanson.dev.movieapp.adapter.ExtraMovieVideoAdapter
import vanson.dev.movieapp.data.api.YOUTUBE_KEY
import vanson.dev.movieapp.data.models.movie.Video
import vanson.dev.movieapp.utils.FullScreenHelper

 @Suppress("DEPRECATION")
class VideoPlayActivity : AppCompatActivity(), ExtraMovieVideoAdapter.Listener {
    private lateinit var fullScreenHelper: FullScreenHelper
    private lateinit var mYoutubePlayer: YouTubePlayer
    private lateinit var mAdapter: ExtraMovieVideoAdapter
    private var mVideos: ArrayList<Video>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)

        ThumbnailLoader.initialize(YOUTUBE_KEY)
        fullScreenHelper = FullScreenHelper(this)

        progress_bar.indeterminateDrawable.setColorFilter(
            0xFFFFFFFF.toInt(),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
//        if(mCurrent == null) mCurrent = intent.getParcelableExtra<Video>("current_video")
        mVideos = intent.getParcelableArrayListExtra<Video>("videos")

        if (mVideos.isNullOrEmpty()) {
            Toast.makeText(this, "No videos for this movie!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            if (mCurrent == null) mCurrent = mVideos!![0]
            play_video_title.text = mCurrent!!.name
            video_thumbnailview.loadThumbnail("https://www.youtube.com/watch?v=" + mCurrent!!.key)
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
                        youTubePlayer.loadVideo(mCurrent!!.key, 0F)
                    } else {
                        youTubePlayer.cueVideo(mCurrent!!.key, 0F)
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
                mAdapter.updateData(mVideos!!.filter { it.id != mCurrent!!.id })
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
            mCurrent = null
            super.onBackPressed()
        }
    }

    override fun onPause() {
        mYoutubePlayer.pause()
        super.onPause()
    }
    companion object {
        private var isFullScreen = false
        private var mCurrent: Video? = null
    }

    override fun changeVideoPlay(video: Video) {
        mCurrent = video
        video_thumbnailview.loadThumbnail("https://www.youtube.com/watch?v=" + video.key)
        play_video_title.text = video.name
        if (lifecycle.currentState == Lifecycle.State.RESUMED) {
            mYoutubePlayer.loadVideo(video.key, 0F)
        } else {
            mYoutubePlayer.cueVideo(video.key, 0F)
        }
        val newData = mVideos!!.filter { it.id != video.id }
        mAdapter.updateData(newData)
    }
}