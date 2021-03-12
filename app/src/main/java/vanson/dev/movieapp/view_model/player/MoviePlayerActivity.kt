package vanson.dev.movieapp.view_model.player

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.keyIterator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_movie_player.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.data.api.TheMovieDBClient
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.models.trailers.Trailer
import vanson.dev.movieapp.data.repository.NetworkState
import vimeoextractor.OnVimeoExtractionListener
import vimeoextractor.VimeoExtractor
import vimeoextractor.VimeoVideo
import java.util.*


@Suppress("DEPRECATION")
class MoviePlayerActivity : AppCompatActivity() {
    private lateinit var mSimpleExoPlayer: SimpleExoPlayer
    private lateinit var mDataSourceFactory: DefaultDataSourceFactory
    private lateinit var mTrailersReponsitory: TrailersMovieRepository
    private lateinit var mTrailersViewModel: TrailersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Set fullscreen
        settingScreen()
        //Setting ex_player
        settingExoplayer()
        //setting view_model
        val movieId = intent.getIntExtra("id_movie", 271110)
        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        mTrailersReponsitory = TrailersMovieRepository(apiService)
        mTrailersViewModel = createViewModelFactory(movieId)

        mTrailersViewModel.trailersMovie.observe(this, Observer {
            if (it.trailers.isNotEmpty()) {
                val first = it.trailers.first()
                if (first.site == "YouTube") {
                    extractTrailerFromYouTube(first)
                } else { //Vimeo
                    extractTrailerFromVimeo(first)
                }
            }else{
                error_text.visibility = View.VISIBLE
            }
        })

        mTrailersViewModel.networkState.observe(this, Observer {
            error_text.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
            progress_load_trailer.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
        })
    }

    private fun createViewModelFactory(movieId: Int) =
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TrailersViewModel(mTrailersReponsitory, movieId) as T
            }
        })[TrailersViewModel::class.java]

    private fun extractTrailerFromVimeo(first: Trailer) {
        VimeoExtractor.getInstance().fetchVideoWithURL(
            URL_VIMEO + first.key,
            null,
            object : OnVimeoExtractionListener {
                override fun onSuccess(video: VimeoVideo) {
                    settingVideoExoplayer(video.streams["${first.size}p"] ?: "", first.name)
                }

                override fun onFailure(throwable: Throwable) {
                    showToast("Have a problem! Try again later...")
                }
            })
    }

    private fun extractTrailerFromYouTube(first: Trailer) {
        @SuppressLint("StaticFieldLeak") val mExtractor: YouTubeExtractor =
            object : YouTubeExtractor(this) {
                override fun onExtractionComplete(
                    sparseArray: SparseArray<YtFile>,
                    videoMeta: VideoMeta
                ) {
                    settingVideoExoplayer(sparseArray.getBestResolutionTrailers(), first.name)
                }
            }
        mExtractor.extract(URL_YOUTUBE + first.key, true, true)
    }

    private fun settingVideoExoplayer(url: String, name: String) {
//        val mediaItem = MediaItem.Builder().setUri(url).setMimeType(MimeTypes.APPLICATION_MPD).build()
//        val mediaItem = MediaItem.fromUri(url)
//        val dataSourceFactory = DefaultHttpDataSourceFactory()
//        val mediaItem = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
//            MediaItem.fromUri(url))
//        val uri = Uri.parse(url)
//        lateinit var mediaSource: MediaSource
//        if (url.toUpperCase(Locale.getDefault()).contains("M3U8")){
//            mediaSource = HlsMediaSource.Factory(mDataSourceFactory).createMediaSource(uri)
//        }else{
//            mediaSource = ExtractorMediaSource(uri, mDataSourceFactory, DefaultExtractorsFactory(), null, null)
//        }
//        val mediaItem = MediaItem.fromUri(url)
        val videoSource = ExtractorMediaSource.Factory(mDataSourceFactory).createMediaSource(Uri.parse(url))
        runOnUiThread {
//            mSimpleExoPlayer.setMediaItem(videoSource)
            mSimpleExoPlayer.prepare(videoSource)
            mSimpleExoPlayer.playWhenReady = true
            title_trailers.text = "  $name"
        }
    }

    private fun settingExoplayer() {
        mSimpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        movie_exo_player.player = mSimpleExoPlayer
        mDataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "appname"))
        mSimpleExoPlayer.addListener(object : Player.EventListener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when(playbackState){
                    Player.STATE_BUFFERING -> Toast.makeText(this@MoviePlayerActivity, "Loading",Toast.LENGTH_SHORT).show()
                    Player.STATE_READY -> Toast.makeText(this@MoviePlayerActivity, "Playing",Toast.LENGTH_SHORT).show()
                    Player.STATE_ENDED -> Toast.makeText(this@MoviePlayerActivity, "Play complete",Toast.LENGTH_SHORT).show()
                    Player.STATE_IDLE -> {}
                }
            }

            override fun onPlayerError(error: ExoPlaybackException) {
                super.onPlayerError(error)
                Toast.makeText(this@MoviePlayerActivity, "Error, check and try again!", Toast.LENGTH_SHORT).show()
            }
        })
        movie_exo_player.setControllerVisibilityListener {
            title_trailers.visibility = it
        }
    }

    private fun settingScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_movie_player)
        supportActionBar?.hide()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSimpleExoPlayer.release()
    }

    companion object {
//        const val VIDEO_TEST_URL = "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4"
        const val URL_YOUTUBE = "https://www.youtube.com/watch?v="
        const val URL_VIMEO = "https://vimeo.com/"
    }

    fun SparseArray<YtFile>.getBestResolutionTrailers(): String{
        var bestResolution = this.keyAt(0)
        for(x in this.keyIterator()){
            if(this[bestResolution].format.height < this[x].format.height && this[x].format.audioBitrate > -1) bestResolution = x
        }
        return this[bestResolution].url
    }
}