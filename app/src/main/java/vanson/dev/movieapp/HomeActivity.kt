package vanson.dev.movieapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_home.*
import org.angmarch.views.OnSpinnerItemSelectedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vanson.dev.movieapp.Adapters.MovieSearchAdapter
import vanson.dev.movieapp.Adapters.PersonSearchAdapter
import vanson.dev.movieapp.Client.RetrofitClient
import vanson.dev.movieapp.Interfaces.RetrofitService
import vanson.dev.movieapp.Models.MoviesResponse
import vanson.dev.movieapp.Models.PersonResponse

class HomeActivity : AppCompatActivity() {
    private lateinit var mRetrofitService: RetrofitService
    private lateinit var movieSearchAdapter: MovieSearchAdapter
    private lateinit var personSearchAdapter: PersonSearchAdapter

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //disable keyboard when start
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        mRetrofitService = RetrofitClient.getClient().create(RetrofitService::class.java)
        results_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //create animation for recycler view
        results_recycler_view.layoutAnimation = AnimationUtils.loadLayoutAnimation(this@HomeActivity, R.anim.layout_slide_right)
        results_recycler_view.scheduleLayoutAnimation()

        Paper.init(this)

        val category = arrayListOf<String>("By Movie Title", "By Person Name")

        source_spinner.attachDataSource(category)
        //get position at start and the set the spinner
        if(Paper.book().contains("position")){
            source_spinner.selectedIndex = Paper.book().read("position")
        }
        val position = source_spinner.selectedIndex

        if(position == 0){
            query_edit_text.hint = "Enter any movie title..."
        }else{
            query_edit_text.hint = "Enter any person name..."
        }

        source_spinner.onSpinnerItemSelectedListener =
            OnSpinnerItemSelectedListener { _, _, position, _ ->
                if(position == 0){
                    query_edit_text.hint = "Enter any movie title..."
                }else{
                    query_edit_text.hint = "Enter any person name..."
                }
            }

        //retrieve the results from paper db and start
        if(Paper.book().contains("cache")){
            val results = Paper.book().read<String>("cache")
            if(Paper.book().contains("source")){
                val source = Paper.book().read<String>("source")
                if(source == "movie"){
                    val moviesResponse = Gson().fromJson(results, MoviesResponse::class.java)
                    if(moviesResponse != null){
                        movieSearchAdapter = MovieSearchAdapter(moviesResponse.movies)
                        results_recycler_view.adapter = movieSearchAdapter
                        //save results to paper database to access offline
                        Paper.book().write("cache", Gson().toJson(moviesResponse))
                        Paper.book().write("source", "movie")
                    }
                }else{
                    val personResponse = Gson().fromJson(results, PersonResponse::class.java)
                    if(personResponse != null){
                        personSearchAdapter = PersonSearchAdapter(this, personResponse.people)
                        results_recycler_view.adapter = personSearchAdapter

                        Paper.book().write("cache", Gson().toJson(personResponse))
                        Paper.book().write("source", "person")
                    }
                }
            }
        }
        search_button.setOnClickListener {
            if(query_edit_text.text != null){
                val text_ = query_edit_text.text.toString()
                if(text_ == "" || text_ == " "){
                    Toast.makeText(this, "Please enter any text...", Toast.LENGTH_SHORT)
                }else{
                    query_edit_text.setText("")
                    val query = text_.replace(" ", "+")
                    if(category.size > 0){
                        val categoryName = category[source_spinner.selectedIndex]
                        if(source_spinner.selectedIndex == 0){
                            val movieResponseCall = mRetrofitService.getMoviesByName(BuildConfig.API_KEY, query)
                            movieResponseCall.enqueue(object : Callback<MoviesResponse>{
                                override fun onResponse(
                                    call: Call<MoviesResponse>,
                                    response: Response<MoviesResponse>
                                ) {
                                    val movieResponse = response.body()
                                    if(movieResponse != null){
                                        movieSearchAdapter = MovieSearchAdapter(movieResponse.movies)
                                        results_recycler_view.adapter = movieSearchAdapter
                                        //save results to paper database to access offline
                                        Paper.book().write("cache", Gson().toJson(movieResponse))
                                        Paper.book().write("source", "movie")
                                    }
                                }

                                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                                    TODO("Not yet implemented")
                                }

                            })
                        }else{
                            val personResponseCall = mRetrofitService.getPersonByName(BuildConfig.API_KEY, query)
                            personResponseCall.enqueue(object : Callback<PersonResponse>{
                                override fun onResponse(
                                    call: Call<PersonResponse>,
                                    response: Response<PersonResponse>
                                ) {
                                    val personResponse = response.body()
                                    if(personResponse != null){
                                        personSearchAdapter = PersonSearchAdapter(this@HomeActivity, personResponse.people)
                                        results_recycler_view.adapter = personSearchAdapter

                                        Paper.book().write("cache", Gson().toJson(personResponse))
                                        Paper.book().write("source", "person")
                                    }
                                }

                                override fun onFailure(call: Call<PersonResponse>, t: Throwable) {
                                    TODO("Not yet implemented")
                                }

                            })
                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()

        //set the position of spinner in offline to retrieve at start
        Paper.book().write("position", source_spinner.selectedIndex)
    }
}