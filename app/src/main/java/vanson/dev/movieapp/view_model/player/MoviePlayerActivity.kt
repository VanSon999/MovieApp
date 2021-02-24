package vanson.dev.movieapp.view_model.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
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
//        val movieId = 43904
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
                    settingVideoExoplayer(video.streams["${first.size}p"] ?: "")
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
                    settingVideoExoplayer(sparseArray[sparseArray.keyAt(0)].url)
                }
            }
        mExtractor.extract(URL_YOUTUBE + first.key, true, true)
    }

    private fun settingVideoExoplayer(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        runOnUiThread {
            mSimpleExoPlayer.setMediaItem(mediaItem)
            mSimpleExoPlayer.prepare()
            mSimpleExoPlayer.playWhenReady = true
        }
    }

    private fun settingExoplayer() {
        mSimpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        movie_exo_player.player = mSimpleExoPlayer
        mDataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "appname"))
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
}