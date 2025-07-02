package com.example.movienight20.ui.details.people

import com.example.movienight20.domain.ActorRoleMovie
import com.example.movienight20.domain.CrewRoleMovie
import kotlin.String

data class PeopleDetailsScreenViewState (
    val actorRoleMovie: List<ActorRoleMovie>,
    val crewRoleMovie: List<CrewRoleMovie>,
    val bio: String,
    val birthday: String,
    val deathday: String,
    val name: String,
    val birthPlace: String,
    val profilePath: String
) {
    companion object {
        val NONE = PeopleDetailsScreenViewState(
            actorRoleMovie = listOf(),
            crewRoleMovie = listOf(),
            bio = "",
            birthday = "",
            deathday = "",
            name = "",
            birthPlace = "",
            profilePath = "",
        )
    }
}