package com.silas.movienight20.data.network_response

import com.google.gson.annotations.SerializedName

class PeopleMovieCreditsNetworkResponse (
    @SerializedName("cast")
    val actorRoleMovie: List<ActorRoleMovie>,
    @SerializedName("crew")
    val crewRoleMovie: List<CrewRoleMovie>
){
    data class ActorRoleMovie(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("release_date")
        val releaseDate: String?,
        @SerializedName("vote_average")
        val voteAvg: String?
    )
    data class CrewRoleMovie(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("release_date")
        val releaseDate: String?,
        @SerializedName("vote_average")
        val voteAvg: String?
    )
}
