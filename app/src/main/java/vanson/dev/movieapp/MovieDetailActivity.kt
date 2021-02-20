package vanson.dev.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*
import java.util.ArrayList

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
        val mData = ArrayList<Cast>()
        mData.add(Cast("CHRIS EVANS", R.drawable.actor_1))
        mData.add(Cast("ROBERT DOWNEY JR.", R.drawable.actor_2))
        mData.add(Cast("SCARLETT JOHANSSON", R.drawable.actor_3))
        mData.add(Cast("STAN LEE", R.drawable.actor_4))

        val castAdapter = CastAdapter(this, mData)
        rv_cast.adapter = castAdapter
        rv_cast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }
}