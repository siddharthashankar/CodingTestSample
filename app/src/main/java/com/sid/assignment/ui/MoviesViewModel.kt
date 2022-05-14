package com.sid.assignment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sid.assignment.data.MoviesRepository
import com.sid.assignment.data.NetworkResult
import com.sid.assignment.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        viewModelScope.launch {
            getNowPlayingMovies()
            getPopularMovies()
            getTopRatedMovies()
            getUpcomingMovies()
        }
    }

    suspend fun getNowPlayingMovies(page: Int = 1) {

        val result = withContext(Dispatchers.IO) {
            repository.getNowPlayingMovies(page)
        }

        result.let { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    _nowPlayingMovies.value = networkResult.data
                }
                is NetworkResult.Error -> {
                    _error.value = true
                }
            }

        }
    }

    suspend fun getPopularMovies(page: Int = 1) {
        val result = withContext(Dispatchers.IO) {
            repository.getPopularMovies(page)
        }

        result.let { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    _popularMovies.value = networkResult.data
                }
            }
        }

    }

    suspend fun getTopRatedMovies(page: Int = 1) {
        val result = withContext(Dispatchers.IO) {
            repository.getTopRatedMovies(page)
        }

        result.let { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    _topRatedMovies.value = networkResult.data

                }
            }
        }
    }

    suspend fun getUpcomingMovies(page: Int = 1) {
        val result = withContext(Dispatchers.IO) {
            repository.getUpcomingMovies(page)
        }

        result.let { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    _upcomingMovies.value = networkResult.data
                }
            }
        }
    }

    suspend fun getSearchMovies(page: Int = 1, query: String) {
        val result= withContext(Dispatchers.IO){
            repository.getSearchMovies(page, query)
        }

        result.let { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    _searchMovies.value = networkResult.data
                }
            }
        }
    }

    private fun onError() {
        _error.value = true
    }
}