package vanson.dev.movieapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewaves.youtubethumbnailview.ThumbnailLoader
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import kotlinx.android.synthetic.main.activity_video_play.*
import kotlinx.android.synthetic.main.video_item_layout.*
import vanson.dev.movieapp.Models.Video
import vanson.dev.movieapp.Utils.FullScreenHelper

@Suppress("DEPRECATION")
class VideoPlayActivity : AppCompatActivity() {
    private lateinit var fullScreenHelper: FullScreenHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)

        ThumbnailLoader.initialize(BuildConfig.YOUTUBE_KEY)
        fullScreenHelper = FullScreenHelper(this)

        progress_bar.indeterminateDrawable.setColorFilter(0xFFFFFFFF.toInt(),android.graphics.PorterDuff.Mode.MULTIPLY);
        val video = intent.getParcelableExtra<Video>("current_video")
        val others = intent.getParcelableArrayListExtra<Video>("videos")

        if(video == null){
            Toast.makeText(this, "No videos for this movie!", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            video_thumbnailview.loadThumbnail("https://www.youtube.com/watch?v=" + video.key)
            video_player_view.enableAutomaticInitialization = false
            if(isFullScreen) video_player_view.enterFullScreen()
            video_player_view.initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    //hide thumbnail and progress bar when video ready to play
                    video_thumbnailview.visibility = View.GONE
                    progress_bar.visibility = View.GONE
                    video_player_view.visibility = View.VISIBLE
                    if(lifecycle.currentState == Lifecycle.State.RESUMED){
                        youTubePlayer.loadVideo(video.key, 0F)
                    }else{
                        youTubePlayer.cueVideo(video.key, 0F)
                    }
                }
            }, true)
            video_player_view.addFullScreenListener(object : YouTubePlayerFullScreenListener{
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
        }
//        if(!others.isNullOrEmpty()){
//            other_videos_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        }
    }

    override fun onBackPressed() {
        if(isFullScreen){
            video_player_view.exitFullScreen()
        }else{
            super.onBackPressed()
        }
    }

    companion object{
        private var isFullScreen = false
    }
}