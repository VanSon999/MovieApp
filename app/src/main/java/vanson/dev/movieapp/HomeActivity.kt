package vanson.dev.movieapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import org.angmarch.views.NiceSpinner
import org.angmarch.views.OnSpinnerItemSelectedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vanson.dev.movieapp.Client.RetrofitClient
import vanson.dev.movieapp.Interfaces.RetrofitService
import vanson.dev.movieapp.Models.MoviesResponse

class HomeActivity : AppCompatActivity() {
    private lateinit var mRetrofitService: RetrofitService
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //disable keyboard when start
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        mRetrofitService = RetrofitClient.getClient().create(RetrofitService::class.java)
        results_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val category = arrayListOf<String>("By Movie Title", "By Person Name")
        val position = source_spinner.selectedIndex

        if(position == 0){
            query_edit_text.setText("Enter any movie title...")
        }else{
            query_edit_text.setText("Enter any person name...")
        }

        source_spinner.setOnSpinnerItemSelectedListener(object : OnSpinnerItemSelectedListener{
            override fun onItemSelected(
                parent: NiceSpinner?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == 0){
                    query_edit_text.setText("Enter any movie title...")
                }else{
                    query_edit_text.setText("Enter any person name...")
                }
            }

        })

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

                                    }
                                }

                                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                                    TODO("Not yet implemented")
                                }

                            })
                        }else{

                        }
                    }
                }
            }
        }
    }
}