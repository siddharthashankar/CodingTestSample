package com.sid.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sid.assignment.data.WatchList
import com.sid.assignment.db.AppDatabase
import javax.inject.Inject

class WatchListViewModel @Inject constructor(
    private val db: AppDatabase
) : ViewModel() {

    private val _all = MutableLiveData<List<WatchList>>()

    val all: LiveData<List<WatchList>>
        get() = _all

    private val _movies = MutableLiveData<List<WatchList>>()

    val movies: LiveData<List<WatchList>>
        get() = _movies

    private val _tvShows = MutableLiveData<List<WatchList>>()

    val tvShows: LiveData<List<WatchList>>
        get() = _tvShows

    init {
        getWatchList()
    }

    fun getWatchList() {
        val movies = db.movieDao().getAll()

        val watchList = mutableListOf<WatchList>()

        watchList.addAll(
            movies.map { movie ->
                WatchList(
                    movie.id,
                    movie.title,
                    movie.overview,
                    movie.posterPath,
                    movie.backdropPath,
                    movie.rating,
                    movie.releaseDate,
                    movie.voteCount
                )
            }
        )

    }

    fun getMovies() {
        val movies = db.movieDao().getAll()

        val watchlist = movies.map { movie ->
            WatchList(
                movie.id,
                movie.title,
                movie.overview,
                movie.posterPath,
                movie.backdropPath,
                movie.rating,
                movie.releaseDate,
                movie.voteCount
            )
        }

        _movies.value = watchlist
    }

}