package com.sid.assignment.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sid.assignment.R
import com.sid.assignment.adapter.MoviesAdapter
import com.sid.assignment.appComponent
import com.sid.assignment.data.MoviesRepository
import com.sid.assignment.model.Movie
import javax.inject.Inject

class NowPlayingFragment :Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: MoviesViewModel

    private lateinit var nowPlayingMovies: RecyclerView
    private lateinit var nowPlayingMoviesAdapter: MoviesAdapter
    private lateinit var nowPlayingMoviesLayoutMgr: LinearLayoutManager
    private var nowPlayingMoviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nowplaying,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Popular Movies
        nowPlayingMovies = view.findViewById(R.id.popular_nowplaying)
        nowPlayingMoviesLayoutMgr = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        nowPlayingMovies.layoutManager = nowPlayingMoviesLayoutMgr
        nowPlayingMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        nowPlayingMovies.adapter = nowPlayingMoviesAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, Observer { movies ->
            nowPlayingMoviesAdapter.appendMovies(movies)
            attachNowPlayingMoviesOnScrollListener()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { onError() })
    }

    private fun attachNowPlayingMoviesOnScrollListener() {
        nowPlayingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = nowPlayingMoviesLayoutMgr.itemCount
                val visibleItemCount = nowPlayingMoviesLayoutMgr.childCount
                val firstVisibleItem = nowPlayingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    nowPlayingMovies.removeOnScrollListener(this)
                    nowPlayingMoviesPage++
                    viewModel.getNowPlayingMovies(nowPlayingMoviesPage)
                }
            }
        })
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_ID, movie.id)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.putExtra(MOVIE_VOTECOUNT, movie.voteCount)
        intent.putExtra(MOVIE_VOTECOUNT, movie.voteCount)
        startActivity(intent)
    }

    private fun onError() {
        Toast.makeText(activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

}