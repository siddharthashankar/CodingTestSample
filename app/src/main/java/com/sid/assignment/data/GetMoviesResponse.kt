package com.sid.assignment.data

import com.google.gson.annotations.SerializedName
import com.sid.assignment.model.Movie

data class GetMoviesResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val movies: List<Movie>,

    @SerializedName("total_pages")
    val pages: Int

)