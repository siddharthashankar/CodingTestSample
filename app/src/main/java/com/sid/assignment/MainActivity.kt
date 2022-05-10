package com.sid.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sid.assignment.ui.*
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sid.assignment.adapter.MoviesAdapter
import com.sid.assignment.data.MoviesRepository
import com.sid.assignment.model.Movie
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: MoviesViewModel

    private lateinit var searchView: SearchView
    private var currentSearchQuery: String = ""
    private lateinit var searchMovies: RecyclerView
    private lateinit var searchMoviesAdapter: MoviesAdapter
    private lateinit var searchMoviesLayoutMgr: LinearLayoutManager
    private var searchMoviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, factory).get(MoviesViewModel::class.java)

        // Initializing the ViewPagerAdapter
        val adapter = ViewPagerAdapter(supportFragmentManager)

        //add fragments
        adapter.addFragment(NowPlayingFragment(), "Now Playing")
        adapter.addFragment(PopularFragment(), "Popular")
        adapter.addFragment(TopRatedFragment(), "Top Rated")
        adapter.addFragment(UpcomingFragment(), "Upcoming")
        adapter.addFragment(WatchListFragment(), "Fav")

        // Adding the Adapter to the ViewPager
        viewPager.adapter = adapter
        //bind the viewPager with the TabLayout.
        tabs.setupWithViewPager(viewPager)

        // Search Movies
        searchMovies = findViewById(R.id.search_movies)
        searchMoviesLayoutMgr = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )
        searchMovies.layoutManager = searchMoviesLayoutMgr
        searchMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        searchMovies.adapter = searchMoviesAdapter

        getSearchMovie()
    }

    override fun onStart() {
        super.onStart()
        viewModel.searchgMovies.observe(this, Observer { movies ->
            searchMoviesAdapter.appendMovies(movies)
            attachSearchMoviesOnScrollListener()
        })

        viewModel.error.observe(this, Observer { onError() })
    }

    private fun onSearchMoviesFetched(movies: List<Movie>) {
        searchMoviesAdapter.appendMovies(movies)
        attachSearchMoviesOnScrollListener()
    }

    private fun attachSearchMoviesOnScrollListener() {
        searchMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = searchMoviesLayoutMgr.itemCount
                val visibleItemCount = searchMoviesLayoutMgr.childCount
                val firstVisibleItem = searchMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    searchMovies.removeOnScrollListener(this)
                    searchMoviesPage++
                    viewModel.getSearchMovies(searchMoviesPage, currentSearchQuery)
                }
            }
        })
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.putExtra(MOVIE_VOTECOUNT, movie.voteCount)
        startActivity(intent)
    }

    private fun onError() {
        Log.i("Error","Something went wrong...")
    }

    private fun getSearchMovie(){
        searchView = findViewById(R.id.search_view)
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.requestFocusFromTouch()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                searchMoviesAdapter.clearMovies()
                currentSearchQuery = newText

                viewModel.getSearchMovies(searchMoviesPage, currentSearchQuery)
                if (currentSearchQuery.length < 1){
                    searchMoviesAdapter.clearMovies()
                    showAppBarAndViewPager()
                } else {
                    hideAppBarAndViewPager()
                }
                return true
            }
        })
    }

    override fun onResume() {
        if (searchView != null) {
            searchView.clearFocus();
        }
        super.onResume()
    }

    fun hideAppBarAndViewPager(){
        appBarLayout.visibility = View.GONE
        viewPager.visibility = View.GONE
        search_movies.visibility = View.VISIBLE
    }

    fun showAppBarAndViewPager(){
        appBarLayout.visibility = View.VISIBLE
        viewPager.visibility = View.VISIBLE
        search_movies.visibility = View.GONE
    }
}