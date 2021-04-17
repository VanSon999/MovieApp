package vanson.dev.movieapp.Utils

import android.app.Activity
import android.view.View

@Suppress("UNCHECKED_CAST", "DEPRECATION")
class FullScreenHelper(private val activity: Activity, vararg views: View) {
    private val views: Array<View> = views as Array<View>
    private fun showSystemUi(decorView: View) {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    private fun hideSystemUi(decorView: View) {
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    fun enterFullScreen() {
        val view = activity.window.decorView
        hideSystemUi(view)
        for (v in views) {
            v.visibility = View.GONE
            v.invalidate()
        }
    }

    fun exitFullScreen() {
        val view = activity.window.decorView
        showSystemUi(view)
        for (v in views) {
            v.visibility = View.VISIBLE
            v.invalidate()
        }
    }

}