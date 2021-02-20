package vanson.dev.movieapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.slide_item.view.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.models.Slide
import vanson.dev.movieapp.ui.MoviePlayerActivity

class SliderPagerAdapter(private val mContext: Context, private val mList: List<Slide>) :
    RecyclerView.Adapter<SliderPagerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            itemView.floatingActionButton.setOnClickListener {
                val intent = Intent(mContext, MoviePlayerActivity::class.java)
                mContext.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = mList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slide_item, parent, false)
//        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view = inflater.inflate(R.layout.slide_item, parent, false)
//        view.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) //fix match_parent pages
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            slide_image.setImageResource(mList[position].getImage())
            slide_title.text = mList[position].getTitle()
        }
    }
}
//class SliderPagerAdapter(private val mContext: Context, private val mList: List<Slide>) :
//    PagerAdapter() {
//    override fun getCount(): Int {
//        return mList.size
//    }
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view == `object`
//    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        val inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view = inflater.inflate(R.layout.slide_item, null)
//        with(view){
//            slide_image.setImageResource(mList[position].getImage())
//            slide_title.text = mList[position].getTitle()
//        }
//        container.addView(view)
//        return view
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        container.removeView(`object` as View?)
//    }
//}