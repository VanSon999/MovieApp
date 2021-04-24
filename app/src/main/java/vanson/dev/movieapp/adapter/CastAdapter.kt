package vanson.dev.movieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cast.view.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.data.models.movie.Cast
import vanson.dev.movieapp.utils.DiffCallBack
import vanson.dev.movieapp.utils.MovieItemClickListener
import vanson.dev.movieapp.utils.loadPhotoImage

class CastAdapter(private val listener: MovieItemClickListener) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    private var mData: List<Cast> = listOf()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener.onCastClick(mData[adapterPosition], it.img_cast)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            img_cast.loadPhotoImage((mData[position].profilePath ?: ""))
//            Glide.with(this.img_cast).load(mData[position].imgLink).into(this.img_cast)
            name_cast.text = mData[position].originalName
            name_character.text =  mData[position].character
        }
    }
    override fun getItemCount() = mData.size

    fun updateCast(newCasts: List<Cast>){
        val diffResult = DiffUtil.calculateDiff(DiffCallBack(mData, newCasts){it.id})
        mData = newCasts
        diffResult.dispatchUpdatesTo(this)
    }
}