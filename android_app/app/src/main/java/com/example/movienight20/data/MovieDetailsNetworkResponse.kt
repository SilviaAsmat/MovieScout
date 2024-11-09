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
    val genres: List<Genre>?
){
    data class Genre(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("genre")
        val title: String?
    )
}