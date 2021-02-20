package vanson.dev.movieapp

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*
import vanson.dev.movieapp.utils.DataSource
import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(), MovieItemClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        //prepare a list of slides...
        val mAdapter = SliderPagerAdapter(DataSource.getListSlide())
        slide_pager.adapter = mAdapter

        TabLayoutMediator(indicator, slide_pager) { tab, _ ->
            tab.select()
        }.attach()

        //setup timer
        val timer = Timer()
        timer.scheduleAtFixedRate(SliderTimer(this, DataSource.getListSlide()),4000, 6000)

        //Recycler View and data
        val movieAdapter = MovieAdapter(DataSource.getPopularMovie(), this)
        rv_movies.adapter = movieAdapter
        rv_movies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //Week movies
        val weekAdapter = MovieAdapter(DataSource.getWeek(), this)
        rv_movies_week.adapter = weekAdapter
        rv_movies_week.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    class SliderTimer(private val activity: Activity, private val slides: List<Slide>) : TimerTask(){
        override fun run() {
            activity.runOnUiThread{
                if(activity.slide_pager.currentItem < slides.lastIndex){
                    activity.slide_pager.currentItem = activity.slide_pager.currentItem + 1
                }else{
                    activity.slide_pager.currentItem = 0
                }
            }
        }
    }

    override fun onMovieClick(movie: Movie, movieImage: ImageView) {
        //send movie info to detail activity and create transition animation between them
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("title", movie.getTitle())
        intent.putExtra("imgURL", movie.getThumbnail())
        intent.putExtra("imgCover", movie.getCoverPhoto())
        //create simple animation
        val options = ActivityOptions.makeSceneTransitionAnimation(this, movieImage, "sharedName")
        startActivity(intent, options.toBundle())
    }
}