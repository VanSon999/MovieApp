package vanson.dev.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ScreenSlidePagerImage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_slide_pager_image)

        val position = intent.getIntExtra("current_postion", -1)
        val images = intent.get
    }
}