package vanson.dev.movieapp.view_model.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import vanson.dev.movieapp.R
import vanson.dev.movieapp.data.api.TheMovieDBClient
import vanson.dev.movieapp.data.api.TheMovieDBInterface
import vanson.dev.movieapp.view_model.home.HomeActivity

open class BaseActivity : AppCompatActivity() {
    private lateinit var mFragment: SearchFragment
    private lateinit var mainLayout: LinearLayout
    //protected...
    protected lateinit var titleActivity: TextView
    protected lateinit var backToHome: ImageView
    protected lateinit var apiService: TheMovieDBInterface

    @SuppressLint("SetTextI18n")
    protected fun init(container: Int, layout: LinearLayout) {
        apiService = TheMovieDBClient.getClient()
        //prepare for fragment
        val searchRepository = SearchRepository(apiService)
        mFragment = SearchFragment(this, searchRepository)
        mainLayout = layout
        val fm = supportFragmentManager.beginTransaction()
        fm.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
        fm.replace(container, mFragment)
        fm.hide(mFragment)
        fm.commit()

        //prepare about UI
        val searchButton = findViewById<View>(R.id.ivSearchIcon) as ImageView
        searchButton.setOnClickListener {
            showFragment()
        }
        titleActivity = findViewById<View>(R.id.title_activity) as TextView
        titleActivity.text = "Movie App"
        backToHome = findViewById<View>(R.id.back_to_home) as ImageView
        backToHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    private fun showFragment() {
        val fm = supportFragmentManager.beginTransaction()
        fm.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
        fm.show(mFragment)
        fm.commit()
        mainLayout.alpha = 0.1F
    }

    private fun hideFragment() {
        val fm = supportFragmentManager.beginTransaction()
        fm.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
        fm.hide(mFragment)
        fm.commit()
        mainLayout.alpha = 1F
        if (currentFocus == null) return
        val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            im.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.message?.let { Log.d(TAG, it) }
        }
    }

    fun toggleShowHideFragment() {
        if (mFragment.isVisible) {
            hideFragment()
        } else {
            showFragment()
        }
    }

    override fun onBackPressed() {
        if (mFragment.isVisible) {
            hideFragment()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val TAG = "BaseActivity"
    }
}
