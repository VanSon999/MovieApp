package vanson.dev.movieapp.view_model.person

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_person_detail.*
import vanson.dev.movieapp.R
import vanson.dev.movieapp.data.api.TheMovieDBClient
import vanson.dev.movieapp.data.models.person.PersonDetails
import vanson.dev.movieapp.data.repository.NetworkState
import vanson.dev.movieapp.utils.loadPersonImage

class PersonDetailActivity : AppCompatActivity() {
    private lateinit var personRepository: PersonRepository
    private lateinit var mViewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)

        //init something
        val personId = intent.getIntExtra("id_person", -1)
        if(personId == -1){
            Toast.makeText(this, "Please select person you want to see!", Toast.LENGTH_SHORT).show()
            finish()
        }
        val apiService = TheMovieDBClient.getClient()
        personRepository = PersonRepository(apiService)
        mViewModel = createViewModelFactory(personId)

        mViewModel.personDetail.observe(this, Observer {
            bindUI(it)
        })

        mViewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            if(it == NetworkState.ERROR){
                Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun bindUI(personDetails: PersonDetails) {
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

        if(birthDay!= null && birthDay.isNotEmpty()){
            person_detail_birthday.text = birthDay
            person_detail_birthday_layout.visibility = View.VISIBLE
        }else{
            person_detail_birthday_layout.visibility = View.GONE
        }

        if(placeOfBirthDay!= null && placeOfBirthDay.isNotEmpty()){
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

    private fun createViewModelFactory(personId: Int) =
        ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PersonViewModel(personRepository, personId) as T
            }
        })[PersonViewModel::class.java]

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
    }
}