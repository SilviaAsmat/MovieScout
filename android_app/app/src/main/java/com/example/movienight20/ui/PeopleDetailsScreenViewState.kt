package com.example.movienight20.ui

import com.example.movienight20.domain.ActorRoleMovie
import com.example.movienight20.domain.CrewRoleMovie

data class PeopleDetailsScreenViewState (
    val actorRoleMovie: List<ActorRoleMovie>,
    val crewRoleMovie: List<CrewRoleMovie>
) {
    companion object {
        val NONE = PeopleDetailsScreenViewState(
            actorRoleMovie = listOf(),
            crewRoleMovie = listOf()
        )
    }
}