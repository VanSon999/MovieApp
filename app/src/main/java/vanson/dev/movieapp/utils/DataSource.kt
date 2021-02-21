package vanson.dev.movieapp.utils

import vanson.dev.movieapp.models.Cast
import vanson.dev.movieapp.models.Movie
import vanson.dev.movieapp.R
import vanson.dev.movieapp.models.Slide

object DataSource {
    private val stSlides: ArrayList<Slide> = ArrayList()
    private val stMovies: ArrayList<Movie>
    private val mCast: ArrayList<Cast>
    private val stWeekMovies: ArrayList<Movie>
    init {
        //Slides
        stSlides.add(Slide(R.drawable.slide2, "Wolverine 2013\nMore text here..."))
        stSlides.add(Slide(R.drawable.slide1, "Captain American: Winter Solider\nMore text here..."))
        stSlides.add(Slide(R.drawable.slide2, "Wolverine 2013\nMore text here..."))
        stSlides.add(Slide(R.drawable.slide1, "Captain American: Winter Solider\nMore text here..."))

        //Movies
        stMovies = ArrayList()
        stMovies.add(Movie(title = "Captain American: Civil War 3 Christ Evan_Jonathan", thumbnail = R.drawable.movie_1, coverPhoto = R.drawable.cover_1))
        stMovies.add(Movie(title = "Spiderman", thumbnail = R.drawable.movie_2, coverPhoto = R.drawable.cover_2))
        stMovies.add(Movie(title = "Infinity War", thumbnail = R.drawable.movie_3, coverPhoto = R.drawable.cover_3))
        stMovies.add(Movie(title = "End Game", thumbnail = R.drawable.movie_4, coverPhoto = R.drawable.cover_4))

        //Cast
        mCast = ArrayList()
        mCast.add(Cast("CHRIS EVANS", R.drawable.actor_1))
        mCast.add(Cast("ROBERT DOWNEY JR.", R.drawable.actor_2))
        mCast.add(Cast("SCARLETT JOHANSSON", R.drawable.actor_3))
        mCast.add(Cast("STAN LEE", R.drawable.actor_4))

        //Week
        stWeekMovies = ArrayList()
        stWeekMovies.add(Movie(title = "Infinity War", thumbnail = R.drawable.movie_3, coverPhoto = R.drawable.cover_3))
        stWeekMovies.add(Movie(title = "End Game", thumbnail = R.drawable.movie_4, coverPhoto = R.drawable.cover_4))
        stWeekMovies.add(Movie(title = "Captain American: Civil War", thumbnail = R.drawable.movie_1, coverPhoto = R.drawable.cover_1))
        stWeekMovies.add(Movie(title = "Spiderman", thumbnail = R.drawable.movie_2, coverPhoto = R.drawable.cover_2))
    }

    fun getPopularMovie() = stMovies
    fun getListSlide() = stSlides
    fun getCast() = mCast
    fun getWeek() = stWeekMovies
}
