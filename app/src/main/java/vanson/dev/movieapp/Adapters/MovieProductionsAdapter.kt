package vanson.dev.movieapp.Adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.production_movie_layout.view.*
import vanson.dev.movieapp.ImageViewerActivity
import vanson.dev.movieapp.Models.ProductionCompany
import vanson.dev.movieapp.R
import vanson.dev.movieapp.Utils.loadBackGroundImage
import vanson.dev.movieapp.Utils.loadPersonImage

class MovieProductionsAdapter(val activity: Activity, val data: List<ProductionCompany>) : RecyclerView.Adapter<MovieProductionsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.production_movie_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            production_company_movie_image.loadBackGroundImage(data[position].logoPath)
            production_company_name.text = data[position].name
            setOnClickListener {
                val intent = Intent(activity, ImageViewerActivity::class.java)
                intent.putExtra("url_image", data[position].logoPath)
                val options = ActivityOptions.makeSceneTransitionAnimation(activity, it, "image_transition")
                activity.startActivity(intent, options.toBundle())
            }
        }
    }

    override fun getItemCount(): Int = data.size
}