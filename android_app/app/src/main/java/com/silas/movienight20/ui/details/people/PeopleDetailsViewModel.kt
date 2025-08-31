package com.silas.movienight20.ui.details.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silas.movienight20.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleDetailsViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
): ViewModel() {
    private val mutableViewState = MutableStateFlow<PeopleDetailsScreenViewState>(PeopleDetailsScreenViewState.NONE)
    val viewState: StateFlow<PeopleDetailsScreenViewState> = mutableViewState

    fun initWithID(id: Int){
        viewModelScope.launch {
            val movieCreditsResult = movieRepo.getPeopleMovieCredits(id)
            val peopleDetailsResult = movieRepo.getPeopleDetails(id)
            val newState = PeopleDetailsScreenViewState(
                actorRoleMovie = movieCreditsResult.actorRoleMovie,
                crewRoleMovie = movieCreditsResult.crewRoleMovie,
                bio = peopleDetailsResult.bio,
                birthday = peopleDetailsResult.birthday,
                deathday = peopleDetailsResult.deathday,
                name = peopleDetailsResult.name,
                birthPlace = peopleDetailsResult.birthPlace,
                profilePath = peopleDetailsResult.profilePath
            )
            mutableViewState.emit(newState)
        }
    }
}