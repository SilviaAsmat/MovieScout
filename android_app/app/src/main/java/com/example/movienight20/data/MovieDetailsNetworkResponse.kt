package com.example.movienight20.data

import com.google.gson.annotations.SerializedName

class MovieDetailsNetworkResponse (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("genres")
    val genres: List<Genre>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val voteAvg: Number?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    @SerializedName("tagline")
    val tagline: String?,
){
    data class Genre(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val title: String?
    )
}