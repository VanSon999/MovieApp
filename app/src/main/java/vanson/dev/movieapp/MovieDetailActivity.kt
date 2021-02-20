package vanson.dev.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieTitle = intent.extras!!.getString("title")
        val imageResourceId = intent.extras!!.getInt("imgURL")
        detail_movie_img.setImageResource(imageResourceId)
    }
}