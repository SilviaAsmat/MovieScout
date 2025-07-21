package com.example.movienight20.domain

data class PeopleMovieCredits (
    val actorRoleMovie: List<ActorRoleMovie>,
    val crewRoleMovie: List<CrewRoleMovie>
)
data class ActorRoleMovie (
    val id: Int,
    val posterPath: String,
    val title: String,
    val releaseDate: String,
    val voteAvg: String
)

data class CrewRoleMovie (
    val id: Int,
    val posterPath: String,
    val title: String,
    val releaseDate: String,
    val voteAvg: String
)




