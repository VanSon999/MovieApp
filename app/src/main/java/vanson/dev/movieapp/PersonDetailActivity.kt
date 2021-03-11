package vanson.dev.movieapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_person_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vanson.dev.movieapp.Client.RetrofitClient
import vanson.dev.movieapp.Interfaces.RetrofitService
import vanson.dev.movieapp.Models.PersonDetails
import vanson.dev.movieapp.Utils.loadPersonImage

class PersonDetailActivity : AppCompatActivity() {
    private lateinit var mRetrofitService: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)
//        Log.d("Person", "onCreate: 123")
        val idPerson = intent.getIntExtra("id_person", -1)
        if(idPerson == -1) {
            Toast.makeText(this, "Please select person you want to see!", Toast.LENGTH_SHORT).show()
            finish()
        }

        mRetrofitService = RetrofitClient.getClient().create(RetrofitService::class.java)
        val personDetailsCall = mRetrofitService.getPersonDetailsById(idPerson, BuildConfig.API_KEY)
        personDetailsCall.enqueue(object : Callback<PersonDetails>{
            override fun onResponse(call: Call<PersonDetails>, response: Response<PersonDetails>) {
                val personDetails = response.body()
                if(personDetails != null){
                    preparePersonDetails(personDetails)
                }else{
                    Toast.makeText(this@PersonDetailActivity, "Any details not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PersonDetails>, t: Throwable) {
                Toast.makeText(this@PersonDetailActivity, "Any details not found", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun preparePersonDetails(personDetails: PersonDetails) {
        person_detail_profile_image_view.loadPersonImage(personDetails.profilePath)
        val name = personDetails.name
        val alsoKnownAs = personDetails.alsoKnownAs
        val birthDay = personDetails.birthday
        val placeOfBirthDay = personDetails.placeOfBirth
        val deathDay = personDetails.deathday
        val department = personDetails.knownForDepartment
        val homePage = personDetails.homepage
        val biography = personDetails.biography

        if(name.isNotEmpty()){
            person_detail_name.text = name
        }

        if(alsoKnownAs.isNotEmpty()){
            person_detail_also_known_as.text = alsoKnownAs.joinToString(",")
            person_detail_also_known_as_layout.visibility = View.VISIBLE
        }else{
            person_detail_also_known_as_layout.visibility = View.GONE
        }

        if(birthDay.isNotEmpty()){
            person_detail_birthday.text = birthDay
            person_detail_birthday_layout.visibility = View.VISIBLE
        }else{
            person_detail_birthday_layout.visibility = View.GONE
        }

        if(placeOfBirthDay.isNotEmpty()){
            person_detail_place_of_birth.text = placeOfBirthDay
            person_detail_place_of_birth_layout.visibility = View.VISIBLE
        }else{
            person_detail_place_of_birth_layout.visibility = View.GONE
        }

        if(deathDay != null && deathDay.isNotEmpty()){
            person_detail_deathday.text = deathDay
            person_detail_deathday_layout.visibility = View.VISIBLE
        }else{
            person_detail_deathday_layout.visibility = View.GONE
        }

        if(department.isNotEmpty()){
            person_detail_known_for_department.text = department
            person_detail_known_for_department_layout.visibility = View.VISIBLE
        }else{
            person_detail_known_for_department_layout.visibility = View.GONE
        }

        if(homePage != null && homePage.isNotEmpty()){
            person_detail_homepage.text = department
            person_detail_homepage_layout.visibility = View.VISIBLE
        }else{
            person_detail_homepage_layout.visibility = View.GONE
        }

        if(biography.isNotEmpty()){
            person_detail_biography.text = department
            person_detail_biography_layout.visibility = View.VISIBLE
        }else{
            person_detail_biography_layout.visibility = View.GONE
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}