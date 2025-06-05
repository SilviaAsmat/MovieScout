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
    private val mutableViewState = MutableStateFlow<List<MovieDetailsScreenViewState>>(emptyList())
    val viewState: StateFlow<List<MovieDetailsScreenViewState>> = mutableViewState
    init {
        viewModelScope.launch {
            val id = 552524 // dummy
            val result = arrayOf(movieRepo.getMovieDetails(id))
            val viewState = result.map {
                val url = "http://image.tmdb.org/t/p/" + "w300" + it.backdropPath
                MovieDetailsScreenViewState(
                    id = TODO(),
                    title = TODO(),
                    backdropPath = TODO(),
                    overview = TODO(),
                    runtime = TODO(),
                    status = TODO(),
                    genres = TODO(),
                )
            }
            mutableViewState.emit(viewState)
        }

    }
}

