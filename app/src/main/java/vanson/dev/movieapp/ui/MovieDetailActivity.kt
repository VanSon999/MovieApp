package vanson.dev.movieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.adapter.CastAdapter
import vanson.dev.movieapp.utils.DataSource

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        val movieTitle = intent.extras!!.getString("title")
        val imageResourceId = intent.extras!!.getInt("imgURL")
        val imageCoverId = intent.extras!!.getInt("imgCover")

        supportActionBar?.title = movieTitle
        detail_movie_title.text = movieTitle
        Glide.with(this).load(imageResourceId).into(detail_movie_img)
        Glide.with(this).load(imageCoverId).into(detail_movie_cover)

        //setup Animation
        detail_movie_cover.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        play_fab.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        //Setup Cast

        val castAdapter = CastAdapter(this, DataSource.getCast())
        rv_cast.adapter = castAdapter
        rv_cast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //Setup FloatingPointButton
        play_fab.setOnClickListener {
            val intent = Intent(this, MoviePlayerActivity::class.java)
            startActivity(intent)
        }

    }
}