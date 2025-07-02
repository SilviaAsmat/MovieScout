package com.example.movienight20.data.network_response

import com.google.gson.annotations.SerializedName

class PopularMoviesNetworkResponse (
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<Item>?,
) {
    data class Item (
        @SerializedName("id")
        val id: Int?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("vote_average")
        val voteAverage: Double?,
        @SerializedName("release_date")
        val releaseDate: String?,
    )
}