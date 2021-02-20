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
import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(), MovieItemClickListener{
    private lateinit var stSlides: ArrayList<Slide>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        //prepare a list of slides...
        stSlides = ArrayList()
        stSlides.add(Slide(R.drawable.slide2, "Wolverine 2013\nMore text here..."))
        stSlides.add(Slide(R.drawable.slide1, "Captain American: Winter Solider\nMore text here..."))
        stSlides.add(Slide(R.drawable.slide2, "Wolverine 2013\nMore text here..."))
        stSlides.add(Slide(R.drawable.slide1, "Captain American: Winter Solider\nMore text here..."))
        val mAdapter = SliderPagerAdapter(stSlides)
        slide_pager.adapter = mAdapter

        TabLayoutMediator(indicator, slide_pager) {tab, position ->
            tab.select()
        }.attach()

        //setup timer
        val timer = Timer()
        timer.scheduleAtFixedRate(SliderTimer(this, stSlides),4000, 6000)

        //Recycler View and data
        val stMovies = ArrayList<Movie>()
        stMovies.add(Movie(title = "Captain American: Civil War", thumbnail = R.drawable.movie_1, coverPhoto = R.drawable.cover_1))
        stMovies.add(Movie(title = "Spiderman", thumbnail = R.drawable.movie_2, coverPhoto = R.drawable.cover_2))
        stMovies.add(Movie(title = "Infinity War", thumbnail = R.drawable.movie_3, coverPhoto = R.drawable.cover_3))
        stMovies.add(Movie(title = "End Game", thumbnail = R.drawable.movie_4, coverPhoto = R.drawable.cover_4))

        val movieAdapter = MovieAdapter(stMovies, this)
        rv_movies.adapter = movieAdapter
        rv_movies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    class SliderTimer(val activity: Activity, val slides: List<Slide>) : TimerTask(){
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