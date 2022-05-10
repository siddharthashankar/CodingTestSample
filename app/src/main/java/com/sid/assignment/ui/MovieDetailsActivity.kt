package com.sid.assignment.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.sid.assignment.R
import com.sid.assignment.appComponent
import com.sid.assignment.db.AppDatabase
import com.sid.assignment.db.MovieEntity
import javax.inject.Inject

const val MOVIE_ID = "extra_movie_id"
const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"
const val MOVIE_VOTECOUNT = "extra_movie_votecount"

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    private lateinit var voteCount: TextView
    private lateinit var addToWatchList: Button

    private var movieId = 0L
    private var movieBackdrop = ""
    private var moviePoster = ""
    private var movieTitle = ""
    private var movieRating = 0f
    private var movieReleaseDate = ""
    private var movieOverview = ""
    private var moviewVoteCount = ""

    @Inject
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)
        voteCount = findViewById(R.id.movie_votecount)
        addToWatchList = findViewById(R.id.add_to_watch_list)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }

    private fun populateDetails(extras: Bundle) {

        movieId = extras.getLong(MOVIE_ID)
        movieBackdrop = extras.getString(MOVIE_BACKDROP, "")
        moviePoster = extras.getString(MOVIE_POSTER, "")
        movieTitle = extras.getString(MOVIE_TITLE, "")
        movieRating = extras.getFloat(MOVIE_RATING, 0f)
        movieReleaseDate = extras.getString(MOVIE_RELEASE_DATE, "")
        moviewVoteCount = extras.getString(MOVIE_VOTECOUNT, "")
        movieOverview = extras.getString(MOVIE_OVERVIEW, "")

        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w200$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        title.text = movieTitle
        rating.rating = movieRating / 2
        releaseDate.text = "Release Date: "+movieReleaseDate
        voteCount.text = "Vote Count: "+moviewVoteCount
        overview.text = movieOverview

        /*title.text = extras.getString(MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = "Release Date: "+extras.getString(MOVIE_RELEASE_DATE, "")
        voteCount.text = "Vote Count: "+extras.getInt(MOVIE_VOTECOUNT, 0).toString()
        overview.text = extras.getString(MOVIE_OVERVIEW, "")*/

        val movie = getMovie(movieId)

        if (movie == null) {
            addToWatchList.text = getString(R.string.add_to_watch_list)
        } else {
            addToWatchList.text = getString(R.string.remove_from_watch_list)
        }
    }

    private fun getMovie(id: Long): MovieEntity? {
        return db.movieDao().findById(id)
    }

    override fun onStart() {
        super.onStart()

        addToWatchList.setOnClickListener {
            if (getMovie(movieId) == null) {
                val entity = MovieEntity(
                        movieId,
                        movieTitle,
                        movieOverview,
                        moviePoster,
                        movieBackdrop,
                        movieRating,
                        movieReleaseDate
                )
                db.movieDao().insert(entity)
                addToWatchList.text = getString(R.string.remove_from_watch_list)
            } else {
                db.movieDao().delete(movieId)
                addToWatchList.text = getString(R.string.add_to_watch_list)
            }
        }
    }
}
