package vanson.dev.movieapp.Adapters

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_item.view.*
import vanson.dev.movieapp.Adapters.commons.SearchViewHolder
import vanson.dev.movieapp.Models.Person
import vanson.dev.movieapp.Models.PersonResponse
import vanson.dev.movieapp.PersonDetailActivity
import vanson.dev.movieapp.R

class PersonSearchAdapter(val activity: Activity, data: List<Person>): RecyclerView.Adapter<SearchViewHolder>() {

    private val mData : List<Person> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        with(holder){
            loadPoster(mData[position].profilePath)
            itemView.poster_title.text = mData[position].name
            itemView.setOnClickListener {
//                Log.d("Hello", "onBindViewHolder: 123")
                val intent = Intent(activity, PersonDetailActivity::class.java)
                intent.putExtra("id_person", mData[position].id)
                activity.startActivity(intent)

                //change animation when cover to new activity
//                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }

    override fun getItemCount(): Int = mData.size
}