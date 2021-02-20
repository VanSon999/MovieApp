package vanson.dev.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_cast.view.*
import vanson.dev.movieapp.models.Cast
import vanson.dev.movieapp.R

class CastAdapter(private val mContext: Context, private val mData: List<Cast>) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            Glide.with(mContext).load(mData[position].getImgLink()).into(this.img_cast)
            name_cast.text = mData[position].getName()
        }
    }

    override fun getItemCount() = mData.size
}