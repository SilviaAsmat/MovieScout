package com.example.movienight20.data.network_response

import com.google.gson.annotations.SerializedName

class MovieCreditsNetworkResponse (
    @SerializedName("id")
    val movieId: Int?,
    @SerializedName("cast")
    val cast: List<Cast>?
) {
    data class Cast(
        @SerializedName("id")
        val castId: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("profile_path")
        val picturePath: String?,
        @SerializedName("character")
        val character: String?
    )
}