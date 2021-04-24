package vanson.dev.movieapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_item.view.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.data.models.movie.Movie
import vanson.dev.movieapp.utils.DiffCallBack
import vanson.dev.movieapp.utils.loadBackImage
import vanson.dev.movieapp.view_model.detail.MovieDetailActivity

class SearchMovieAdapter(private val activity: Activity) :
    RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>() {

    private var mData: List<Movie> = listOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val intent = Intent(activity, MovieDetailActivity::class.java)
                intent.putExtra("id_movie", mData[adapterPosition].id)
                activity.startActivity(intent)
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            diagonal_image.loadBackImage(mData[position].posterPath ?: "")
            poster_title.text = mData[position].title
        }
    }

    override fun getItemCount(): Int = mData.size

    fun updateData(newData: List<Movie>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallBack(mData, newData) { it.id })
        mData = newData
        diffResult.dispatchUpdatesTo(this)
    }
}
