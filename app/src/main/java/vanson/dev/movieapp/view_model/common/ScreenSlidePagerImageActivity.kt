package vanson.dev.movieapp.view_model.common

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_screen_slide_pager_image.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.adapter.ScreenSlidePagerAdapter
import vanson.dev.movieapp.utils.ZoomOutPageTransformer

class ScreenSlidePagerImageActivity : FragmentActivity() {
    private lateinit var mAdapter: ScreenSlidePagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_slide_pager_image)

        val position = intent.getIntExtra("current_position", -1)
        val images = intent.getStringArrayExtra("images")

        if (position == -1 || images.isNullOrEmpty()) {
            Toast.makeText(this, "Nothing to show!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            pager_image.setPageTransformer(ZoomOutPageTransformer())
            mAdapter = ScreenSlidePagerAdapter(this, images)
            pager_image.adapter = mAdapter
            pager_image.currentItem = position
        }
    }
}