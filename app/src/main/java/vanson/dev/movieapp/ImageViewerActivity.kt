package vanson.dev.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_viewer.*
import vanson.dev.movieapp.BuildConfig.BASE_URL_SEARCH

class ImageViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)

        @Suppress("DEPRECATION")
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val imageUrl = intent.getStringExtra("url_image")
        if(imageUrl != null){
            Picasso.get().load(BASE_URL_SEARCH + imageUrl).into(zoom_image_view)
        }else{
            Toast.makeText(this, "Please select image you want to see!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}