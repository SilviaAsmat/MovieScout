package com.example.movienight20.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienight20.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
) : ViewModel() {

    private val _resultsViewState =
        MutableStateFlow<SearchMovieResultsViewState>(SearchMovieResultsViewState.Loading)
    val resultsViewState: StateFlow<SearchMovieResultsViewState> = _resultsViewState

    private val _searchViewState = MutableStateFlow<SearchBarViewState>(SearchBarViewState.NONE)
    val searchViewState: StateFlow<SearchBarViewState> = _searchViewState

//    init {
//        performSearch("")
//    }

    fun onSearchQueryChanged(query: String) {
        _searchViewState.value = SearchBarViewState(query)
    }

    fun performSearch(query: String) {
        viewModelScope.launch {
            _resultsViewState.value = SearchMovieResultsViewState.Loading

            val searchResults = movieRepo.getMovieSearch(query)
            val newResults = searchResults.map {
                Results(
                    id = it.id,
                    title = it.title,
                    backdropPath = it.backdropPath,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    rating = it.rating
                )
            }
            _resultsViewState.value = SearchMovieResultsViewState.Data(newResults)
        }
    }
}

