package vanson.dev.movieapp.view_model.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_image_viewer.view.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.utils.loadImage

class ScreenSlidePageFragment(private val url: String?) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.activity_image_viewer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.zoom_image_view.loadImage(url ?: "")
        super.onViewCreated(view, savedInstanceState)
    }
}
