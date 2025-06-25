package com.example.movienight20.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienight20.domain.MoviesRepository
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
            val result = movieRepo.getPeopleMovieCredits(id)
            val newState = PeopleDetailsScreenViewState(
                actorRoleMovie = result.actorRoleMovie,
                crewRoleMovie = result.crewRoleMovie
            )
            mutableViewState.emit(newState)
        }
    }
}