package com.example.movienight20.data

import com.google.gson.annotations.SerializedName

class PopularMoviesNetworkResponse (
    // Where is page needed?
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<Item>?,
) {
    class Item (
        @SerializedName("id")
        val id: Int?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        @SerializedName("vote_average")
        val voteAverage: Double?,
        @SerializedName("release_date")
        val releaseDate: String?,
    )
}