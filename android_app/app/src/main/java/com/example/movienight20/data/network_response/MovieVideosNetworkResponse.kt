package com.example.movienight20.data.network_response

import com.google.gson.annotations.SerializedName

class MovieVideosNetworkResponse (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val result: List<Info>
){
    data class Info(
        @SerializedName("key")
        val key: String?,
        @SerializedName("name")
        val title: String?,
        @SerializedName("official")
        val official: Boolean?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("size")
        val size: Int?
    )
}