package vanson.dev.movieapp.view_model.common

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_image_viewer.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.utils.loadImage

class ImageViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)

        @Suppress("DEPRECATION")
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val imageUrl = intent.getStringExtra("url_image")
        if(imageUrl != null){
            zoom_image_view.loadImage(imageUrl)
        }else{
            Toast.makeText(this, "Please select image you want to see!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}