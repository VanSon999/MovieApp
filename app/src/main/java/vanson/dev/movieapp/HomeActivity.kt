package vanson.dev.movieapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*
import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {
    private lateinit var stSlides: ArrayList<Slide>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //prepare a list of slides...
        stSlides = ArrayList()
        stSlides.add(Slide(R.drawable.slide2, "Wolverine 2013\nMore text here..."))
        stSlides.add(Slide(R.drawable.slide1, "Captain American: Winter Solider\nMore text here..."))
        stSlides.add(Slide(R.drawable.slide2, "Wolverine 2013\nMore text here..."))
        stSlides.add(Slide(R.drawable.slide1, "Captain American: Winter Solider\nMore text here..."))

        val mAdapter = SliderPagerAdapter(this, stSlides)
        slide_pager.adapter = mAdapter

        TabLayoutMediator(indicator, slide_pager) {tab, position ->
            tab.select()
        }.attach()

        //setup timer
        val timer = Timer()
        timer.scheduleAtFixedRate(SliderTimer(this, stSlides),4000, 6000)
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
}