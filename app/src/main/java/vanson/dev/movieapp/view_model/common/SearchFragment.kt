package vanson.dev.movieapp.view_model.common

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vanson.dev.movieapp.R
import vanson.dev.movieapp.adapter.SearchMovieAdapter
import vanson.dev.movieapp.adapter.SearchPersonAdapter
import vanson.dev.movieapp.data.repository.NetworkState

class SearchFragment(private val listener: BaseActivity, private val repository: SearchRepository) :
    Fragment(),
    TextWatcher {
    private lateinit var mViewModel: SearchViewModel
    private var isMovie: Boolean = true
    private lateinit var editText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var notFound: TextView
    private lateinit var movieAdapter: SearchMovieAdapter
    private lateinit var personAdapter: SearchPersonAdapter
    private var isSearchEntered = false

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        val mView = inflater.inflate(R.layout.fragment_search, container, false)

        val ivBackArrow = mView.findViewById<View>(R.id.ivBackArrow) as ImageView
        ivBackArrow.setOnClickListener {
            listener.toggleShowHideFragment()
        }

        recyclerView = mView.findViewById(R.id.search_recycler_view) as RecyclerView
        notFound = mView.findViewById(R.id.not_found) as TextView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutAnimation =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slide_bottom)
        recyclerView.scheduleLayoutAnimation()
        //editText
        editText = mView.findViewById<View>(R.id.search_text) as EditText

        //optionsSearch Spinner
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
                notFound.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                if (isMovie) {
                    editText.hint = "Search Movies"
                    recyclerView.adapter = movieAdapter
                } else {
                    editText.hint = "Search Actors"
                    recyclerView.adapter = personAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected: Nothing selected!!!")
            }
        }
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = createViewModelFactory()
        movieAdapter = SearchMovieAdapter(listener)
        personAdapter = SearchPersonAdapter(listener)

        mViewModel.moviesResponse.observe(viewLifecycleOwner, {
            val movies = it.movies
            if (movies.isEmpty()) {
                recyclerView.visibility = View.GONE
                notFound.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                notFound.visibility = View.GONE
            }
            movieAdapter.updateData(movies)
        })

        mViewModel.peopleResponse.observe(viewLifecycleOwner, {
            val people = it.people
            if (people.isEmpty()) {
                recyclerView.visibility = View.GONE
                notFound.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                notFound.visibility = View.GONE
            }
            personAdapter.updateData(people)
        })

        mViewModel.networkState.observe(viewLifecycleOwner, {
            if (it == NetworkState.ERROR) {
                Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
            }
        })
        editText.addTextChangedListener(this)
    }

    private fun createViewModelFactory() =
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(repository) as T
            }
        })[SearchViewModel::class.java]

    companion object {
        private const val TAG = "ToolbarFragment"
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (!isSearchEntered) {
            isSearchEntered = true
            @Suppress("DEPRECATION")
            Handler().postDelayed({
                isSearchEntered = false
                if (isMovie) {
                    mViewModel.searchMovieByName(editText.text.toString())
                } else {
                    mViewModel.searchPersonByName(editText.text.toString())
                }
            }, 500)
        }
    }
}