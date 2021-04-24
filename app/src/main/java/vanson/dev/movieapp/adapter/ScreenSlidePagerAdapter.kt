package vanson.dev.movieapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import vanson.dev.movieapp.view_model.common.ScreenSlidePageFragment

class ScreenSlidePagerAdapter(fa: FragmentActivity, private val mData: Array<String>) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = mData.size

    override fun createFragment(position: Int): Fragment = ScreenSlidePageFragment(mData[position])
}
