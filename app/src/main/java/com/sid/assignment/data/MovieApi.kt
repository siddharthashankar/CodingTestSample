package com.sid.assignment.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApi {
    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = "0e7274f05c36db12cbe71d9ab0393d47",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "0e7274f05c36db12cbe71d9ab0393d47",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "0e7274f05c36db12cbe71d9ab0393d47",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "0e7274f05c36db12cbe71d9ab0393d47",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("search/movie")
    fun getSearchMovies(
            @Query("api_key") apiKey: String = "0e7274f05c36db12cbe71d9ab0393d47",
            @Query("page") page: Int,
            @Query("query") query: String //= "Harry Potter"
    ): Call<GetMoviesResponse>
}

//https://api.themoviedb.org/3/search/movie?query=fi&api_key=0e7274f05c36db12cbe71d9ab0393d47