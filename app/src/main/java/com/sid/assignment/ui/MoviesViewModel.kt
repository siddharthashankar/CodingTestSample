package com.sid.assignment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sid.assignment.data.MoviesRepository
import com.sid.assignment.model.Movie
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>>
        get() = _nowPlayingMovies

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>>
    get() = _popularMovies

    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>>
    get() = _topRatedMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>>
    get() = _upcomingMovies

    private val _searchMovies = MutableLiveData<List<Movie>>()
    val searchgMovies: LiveData<List<Movie>>
        get() = _searchMovies

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    init {
        getNowPlayingMovies()
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    fun getNowPlayingMovies(page: Int = 1){
        repository.getNowPlayingMovies(
            page,
            ::onNowPlayingMovieFetched,
            ::onError
        )
    }

    private fun onNowPlayingMovieFetched(movies: List<Movie>) {
        _nowPlayingMovies.value = movies
    }

    fun getPopularMovies(page: Int = 1) {
        repository.getPopularMovies(
            page,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        _popularMovies.value = movies
    }

    fun getTopRatedMovies(page: Int = 1) {
        repository.getTopRatedMovies(
            page,
            ::onTopRatedMoviesFetched,
            ::onError
        )
    }

    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        _topRatedMovies.value = movies
    }

    fun getUpcomingMovies(page: Int = 1) {
        repository.getUpcomingMovies(
            page,
            ::onUpcomingMoviesFetched,
            ::onError
        )
    }

    private fun onUpcomingMoviesFetched(movies: List<Movie>) {
        _upcomingMovies.value = movies
    }

    fun getSearchMovies(page: Int = 1, query: String,) {
        repository.getSearchMovies(
            page,
            query,
            ::onSearchMoviesFetched,
            ::onError
        )
    }

    private fun onSearchMoviesFetched(movies: List<Movie>) {
        _searchMovies.value = movies
    }

    private fun onError() {
        _error.value = true
    }
}