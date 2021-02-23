package vanson.dev.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_cast.view.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.data.api.POSTER_BASE_URL
import vanson.dev.movieapp.data.vo.Cast
import vanson.dev.movieapp.utils.DiffCallBack
import vanson.dev.movieapp.utils.loadPhotoImage

class CastAdapter() : RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    private var mData: List<Cast> = listOf()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            img_cast.loadPhotoImage(POSTER_BASE_URL + (mData[position].profilePath ?: ""))
//            Glide.with(this.img_cast).load(mData[position].imgLink).into(this.img_cast)
            name_cast.text = mData[position].originalName
        }
    }
    override fun getItemCount() = mData.size

    fun updateCast(newCasts: List<Cast>){
        val diffResult = DiffUtil.calculateDiff(DiffCallBack(mData, newCasts){it.id})
        mData = newCasts
        diffResult.dispatchUpdatesTo(this)
    }
}