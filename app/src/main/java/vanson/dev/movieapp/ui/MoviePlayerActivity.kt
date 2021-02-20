package vanson.dev.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_movie_player.*
import vanson.dev.movieapp.R

@Suppress("DEPRECATION")
class MoviePlayerActivity : AppCompatActivity() {
    private lateinit var mSimpleExoPlayer: SimpleExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Set fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_movie_player)
        supportActionBar?.hide()
        //Setting ex_player
        mSimpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        movie_exo_player.player = mSimpleExoPlayer
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "appname"))
        val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
            MediaItem.fromUri(VIDEO_TEST_URL))
        mSimpleExoPlayer.setMediaSource(videoSource)
        mSimpleExoPlayer.prepare()
        mSimpleExoPlayer.playWhenReady = true
    }

    override fun onDestroy() {
        super.onDestroy()
        mSimpleExoPlayer.release()
    }

    companion object{
        const val VIDEO_TEST_URL = "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4"
    }
}