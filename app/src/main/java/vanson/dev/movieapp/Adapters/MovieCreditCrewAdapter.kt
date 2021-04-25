package vanson.dev.movieapp.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.credit_movie_layout.view.*
import vanson.dev.movieapp.Models.Crew
import vanson.dev.movieapp.PersonDetailActivity
import vanson.dev.movieapp.R
import vanson.dev.movieapp.Utils.loadPersonImage

class MovieCreditCrewAdapter(val activity: Activity, val data: List<Crew>) : RecyclerView.Adapter<MovieCreditCrewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.credit_movie_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            credit_movie_image.loadPersonImage(data[position].profilePath)
            credit_name.text = data[position].originalName
            credit_character.text = "Department: ${data[position].department}"
            credit_layout.setOnClickListener {
                val intent = Intent(context, PersonDetailActivity::class.java)
                intent.putExtra("id_person", data[position].id)
                activity.startActivity(intent)
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}