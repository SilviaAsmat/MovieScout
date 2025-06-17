package com.example.movienight20.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienight20.domain.MoviesRepository
import com.example.movienight20.ui.MovieDetailsScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
): ViewModel() {
    private val mutableViewState = MutableStateFlow<MovieDetailsScreenViewState>(MovieDetailsScreenViewState.NONE)
    val viewState: StateFlow<MovieDetailsScreenViewState> = mutableViewState

    fun initWithID(id: Int){
        viewModelScope.launch {
            val result = movieRepo.getMovieDetails(id)
            val newState = MovieDetailsScreenViewState(
                id = result.id,
                title = result.title,
                backdropPath = "http://image.tmdb.org/t/p/" + "w300" + result.backdropPath,
                overview = result.overview,
                runtime = result.runtime,
                status = result.status,
                genres = result.genres,
            )

            mutableViewState.emit(newState)
        }
    }
}

