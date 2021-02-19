package vanson.dev.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

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
    }
}