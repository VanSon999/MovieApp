package vanson.dev.movieapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.slide_item.view.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.data.api.POSTER_BASE_URL
import vanson.dev.movieapp.data.models.movie_details.Movie
import vanson.dev.movieapp.models.Slide
import vanson.dev.movieapp.utils.DiffCallBack
import vanson.dev.movieapp.utils.MovieItemClickListener
import vanson.dev.movieapp.utils.loadBackImage
import vanson.dev.movieapp.view_model.player.MoviePlayerActivity

class SliderPagerAdapter(private val listener: MovieItemClickListener) :
    RecyclerView.Adapter<SliderPagerAdapter.ViewHolder>() {
    private var mData: List<Movie> = listOf()
    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        init {
            this.view.slide_image.setOnClickListener {
                listener.onMovieClick(mData[adapterPosition], this.view.slide_image)
            }
            this.view.floatingActionButton.setOnClickListener {
                listener.onPlayClick(mData[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = mData.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slide_item, parent, false)
        return ViewHolder(view)
//        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view = inflater.inflate(R.layout.slide_item, parent, false)
//        view.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) //fix match_parent pages
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view){
            slide_image.loadBackImage(POSTER_BASE_URL + mData[position].backdropPath)
            slide_title.text = mData[position].title + "\nMore info..."
        }
    }

    fun updateMovie(newMovies: List<Movie>){
        val diffResult = DiffUtil.calculateDiff(DiffCallBack(mData, newMovies){it.id})
        mData = newMovies
        diffResult.dispatchUpdatesTo(this)
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