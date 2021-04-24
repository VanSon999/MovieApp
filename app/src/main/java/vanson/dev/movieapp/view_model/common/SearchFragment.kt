package vanson.dev.movieapp.view_model.common

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import vanson.dev.movieapp.R

class SearchFragment(private val listener: BaseActivity, ) : Fragment() {
    private lateinit var mView: View
    private var isMovie: Boolean = true
    private lateinit var editText: EditText
    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_search, container, false)

        val ivBackArrow = mView.findViewById<View>(R.id.ivBackArrow) as ImageView
        ivBackArrow.setOnClickListener {
            listener.toggleShowHideFragment()
        }
        recyclerView = mView.findViewById(R.id.search_recycler_view) as RecyclerView
        editText = mView.findViewById<View>(R.id.search_text) as EditText
        val optionSearch = mView.findViewById<View>(R.id.options_search) as Spinner
        optionSearch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                editText.setText("")
                isMovie = position == 0
                editText.hint = if (isMovie) {
                    "Search Movies"
                } else {
                    "Search Actors"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected: Nothing selected!!!")
            }
        }
        return mView
    }

    companion object {
        private const val TAG = "ToolbarFragment"
    }
}