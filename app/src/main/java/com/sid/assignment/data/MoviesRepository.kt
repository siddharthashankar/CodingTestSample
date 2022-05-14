package com.sid.assignment.data

import com.sid.assignment.model.Movie
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MoviesRepository @Inject constructor(private val movieApi: MovieApi) {

    suspend fun getNowPlayingMovies(page: Int = 1): NetworkResult<List<Movie>> {
        return suspendCoroutine { continuation ->
            movieApi.getNowPlayingMovies(page = page)
                    .enqueue(object : Callback<GetMoviesResponse> {
                        override fun onResponse(call: Call<GetMoviesResponse>, response: Response<GetMoviesResponse>) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()
                                if (responseBody != null) {
                                    continuation.resume(NetworkResult.Success(responseBody.movies))
                                } else {
                                    NetworkResult.Success(response.message())
                                }
                            }
                        }

                        override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                            continuation.resume(NetworkResult.Error(call.toString() + "," + t.message))
                        }

                    })
        }
    }

    suspend fun getPopularMovies(page: Int = 1): NetworkResult<List<Movie>> {
        return suspendCoroutine { continuation ->
            movieApi.getPopularMovies(page = page)
                    .enqueue(object : Callback<GetMoviesResponse> {
                        override fun onResponse(
                                call: Call<GetMoviesResponse>,
                                response: Response<GetMoviesResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()

                                if (responseBody != null) {
                                    continuation.resume(NetworkResult.Success(responseBody.movies))
                                } else {
                                    NetworkResult.Success(response.message())
                                }
                            } else {
                                NetworkResult.Success(response.message())
                            }
                        }

                        override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                            continuation.resume(NetworkResult.Error(call.toString() + "," + t.message))
                        }
                    })
        }
    }

    suspend fun getTopRatedMovies(page: Int = 1): NetworkResult<List<Movie>> {
        return suspendCoroutine { continuation ->
            movieApi.getTopRatedMovies(page = page)
                    .enqueue(object : Callback<GetMoviesResponse> {
                        override fun onResponse(
                                call: Call<GetMoviesResponse>,
                                response: Response<GetMoviesResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()

                                if (responseBody != null) {
                                    continuation.resume(NetworkResult.Success(responseBody.movies))
                                } else {
                                    NetworkResult.Success(response.message())
                                }
                            } else {
                                NetworkResult.Success(response.message())
                            }
                        }

                        override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                            continuation.resume(NetworkResult.Error(call.toString() + "," + t.message))
                        }
                    })
        }
    }

    suspend fun getUpcomingMovies(page: Int = 1): NetworkResult<List<Movie>> {
        return suspendCoroutine { continuation ->
            movieApi.getUpcomingMovies(page = page)
                    .enqueue(object : Callback<GetMoviesResponse> {
                        override fun onResponse(
                                call: Call<GetMoviesResponse>,
                                response: Response<GetMoviesResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()

                                if (responseBody != null) {
                                    continuation.resume(NetworkResult.Success(responseBody.movies))
                                } else {
                                    NetworkResult.Success(response.message())
                                }
                            } else {
                                NetworkResult.Success(response.message())
                            }
                        }

                        override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                            continuation.resume(NetworkResult.Error(call.toString() + "," + t.message))
                        }
                    })
        }
    }

    suspend fun getSearchMovies(page: Int = 1, query: String): NetworkResult<List<Movie>> {
        return suspendCoroutine { continuation ->
            movieApi.getSearchMovies(page = page, query = query)
                    .enqueue(object : Callback<GetMoviesResponse> {
                        override fun onResponse(
                                call: Call<GetMoviesResponse>,
                                response: Response<GetMoviesResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()

                                if (responseBody != null) {
                                    continuation.resume(NetworkResult.Success(responseBody.movies))
                                } else {
                                    NetworkResult.Success(response.message())
                                }
                            } else {
                                NetworkResult.Success(response.message())
                            }
                        }

                        override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                            continuation.resume(NetworkResult.Error(call.toString() + "," + t.message))
                        }
                    })
        }
    }
}