package com.example.movienight20.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienight20.domain.MoviesRepository
import com.example.movienight20.ui.MovieListItemViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
): ViewModel() {
    private val mutableViewState = MutableStateFlow<List<MovieListItemViewState>>(emptyList())
    val viewState: StateFlow<List<MovieListItemViewState>> = mutableViewState

    init {
        viewModelScope.launch {
            val result = movieRepo.getMovies()
            val viewStates = result.map {
                val releaseYear = it.releaseDate.substringBefore("-")
                val url = "http://image.tmdb.org/t/p/" + "w300" + it.posterPath
                MovieListItemViewState(id = it.id, title = it.title, url = url, year = releaseYear, rating = it.rating)
            }
            mutableViewState.emit(viewStates)
        }

    }
}

