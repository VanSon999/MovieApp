package vanson.dev.movieapp.view_model.player

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewaves.youtubethumbnailview.ThumbnailLoader
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import kotlinx.android.synthetic.main.activity_video_play.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.adapter.ExtraMovieVideoAdapter
import vanson.dev.movieapp.data.api.TheMovieDBClient
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.data.api.YOUTUBE_KEY
import vanson.dev.movieapp.data.models.trailers.Trailer
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.utils.FullScreenHelper

@Suppress("DEPRECATION")
class VideoPlayActivity : AppCompatActivity(), ExtraMovieVideoAdapter.Listener {
    private lateinit var fullScreenHelper: FullScreenHelper
    private lateinit var mYoutubePlayer: YouTubePlayer
    private lateinit var mAdapter: ExtraMovieVideoAdapter
    private lateinit var mTrailersRepository: TrailersMovieRepository
    private lateinit var mTrailersViewModel: TrailersViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)

        val idMovie = intent.getIntExtra("movie_id", -1)
        if (idMovie == -1) {
            Toast.makeText(this, "Please select movie!", Toast.LENGTH_SHORT).show()
            finish()
        }
        //init ViewModel and Repository
        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        mTrailersRepository = TrailersMovieRepository(apiService)
        mTrailersViewModel = createViewModelFactory(idMovie)

        //-------------------------------------
        ThumbnailLoader.initialize(YOUTUBE_KEY)
        fullScreenHelper = FullScreenHelper(this)
        progress_bar.indeterminateDrawable.setColorFilter(
            0xFFFFFFFF.toInt(),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )

        setupYoutubePlayer()
        setupAdapter()

        mTrailersViewModel.trailersMovie.observe(this, {
            if (it.trailers.isNullOrEmpty()) {
                other_videos_recycler.visibility = View.GONE
                no_result_found.visibility = View.VISIBLE
                no_result_found.text = "Not Found Any Video!"
            } else {
                if (it.trailers.size == 1) {
                    other_videos_recycler.visibility = View.GONE
                    no_result_found.visibility = View.VISIBLE
                } else {
                    other_videos_recycler.visibility = View.VISIBLE
                    no_result_found.visibility = View.GONE
                }
                if (mCurrent == null) mCurrent = it.trailers[0]
                play_video_title.text = mCurrent!!.name
                video_thumbnailview.loadThumbnail("https://www.youtube.com/watch?v=" + mCurrent!!.key)
                mAdapter.updateData(mCurrent!!, it.trailers)
                waitYoutubePlayerInit(mCurrent!!.key)
            }
        })

        mTrailersViewModel.networkState.observe(this, {
            if (it == NetworkState.ERROR) {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
//        if (mCurrent == null) mCurrent = mVideos!![0]
//        play_video_title.text = mCurrent!!.name
//        video_thumbnailview.loadThumbnail("https://www.youtube.com/watch?v=" + mCurrent!!.key)
//        video_player_view.enableAutomaticInitialization = false
//        video_player_view.initialize(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                super.onReady(youTubePlayer)
//                mYoutubePlayer = youTubePlayer
//                //hide thumbnail and progress bar when video ready to play
//                video_thumbnailview.visibility = View.GONE
//                progress_bar.visibility = View.GONE
//                video_player_view.visibility = View.VISIBLE
//                if (lifecycle.currentState == Lifecycle.State.RESUMED) {
//                    youTubePlayer.loadVideo(mCurrent!!.key, 0F)
//                } else {
//                    youTubePlayer.cueVideo(mCurrent!!.key, 0F)
//                }
//            }
//        }, true)
//        video_player_view.addFullScreenListener(object : YouTubePlayerFullScreenListener {
//            override fun onYouTubePlayerEnterFullScreen() {
//                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//                fullScreenHelper.enterFullScreen()
//                isFullScreen = true
//            }
//
//            override fun onYouTubePlayerExitFullScreen() {
//                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//                fullScreenHelper.exitFullScreen()
//                isFullScreen = false
//            }
//        })
//        if (isFullScreen) video_player_view.enterFullScreen()
//
//        // recycler others
//        if (mVideos!!.size <= 1) {
//            other_videos_recycler.visibility = View.GONE
//            no_result_found.visibility = View.VISIBLE
//        } else {
//            other_videos_recycler.visibility = View.VISIBLE
//            no_result_found.visibility = View.GONE
//            other_videos_recycler.layoutManager =
//                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//            mAdapter = ExtraMovieVideoAdapter(this, this)
//            other_videos_recycler.adapter = mAdapter
//            mAdapter.updateData(mVideos!!.filter { it.id != mCurrent!!.id })
//            other_videos_recycler.layoutAnimation =
//                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_slide_bottom)
//            other_videos_recycler.scheduleLayoutAnimation()
//        }
    }

    private fun waitYoutubePlayerInit(key: String) {
        Handler().postDelayed({
            if (this::mYoutubePlayer.isInitialized) {
                mYoutubePlayer.loadOrCueVideo(lifecycle, key, 0F)
            } else {
                waitYoutubePlayerInit(key)
            }
        }, 500)
    }

    private fun setupAdapter() {
        other_videos_recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = ExtraMovieVideoAdapter(this, this)
        other_videos_recycler.adapter = mAdapter
        other_videos_recycler.layoutAnimation =
            AnimationUtils.loadLayoutAnimation(this, R.anim.layout_slide_bottom)
        other_videos_recycler.scheduleLayoutAnimation()
    }

    private fun setupYoutubePlayer() {
        video_player_view.enableAutomaticInitialization = false
        video_player_view.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                video_thumbnailview.visibility = View.GONE
                progress_bar.visibility = View.GONE
                video_player_view.visibility = View.VISIBLE
                mYoutubePlayer = youTubePlayer
            }
        }, true)

//        mYoutubePlayer.addListener(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                Log.d("Alo", "onReady mYoutubePlayer:")
//            }
//        })

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
    }

    private fun createViewModelFactory(movieId: Int) =
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TrailersViewModel(mTrailersRepository, movieId) as T
            }
        })[TrailersViewModel::class.java]

    override fun onBackPressed() {
        if (isFullScreen) {
            video_player_view.exitFullScreen()
        } else {
//            mCurrent = null
            super.onBackPressed()
        }
    }

    override fun onPause() {
        mYoutubePlayer.pause()
        super.onPause()
    }

    companion object {
        private var isFullScreen = false
        private var mCurrent: Trailer? = null
    }

    override fun changeVideoPlay(video: Trailer, mData: List<Trailer>) {
        mCurrent = video
        video_thumbnailview.loadThumbnail("https://www.youtube.com/watch?v=" + video.key)
        play_video_title.text = video.name
        waitYoutubePlayerInit(video.key)
        mAdapter.updateData(video, mData)
    }

}