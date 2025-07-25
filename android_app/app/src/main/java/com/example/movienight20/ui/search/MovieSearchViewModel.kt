package com.example.movienight20.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienight20.domain.MoviesRepository
import com.example.movienight20.ui.movie_list.MovieListItemViewState
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
        Log.v("SAA", "got new term: $query")
        _searchViewState.value = SearchBarViewState(query)
        performSearch(query)
    }

    fun performSearch(query: String) {
        viewModelScope.launch {
            _resultsViewState.value = SearchMovieResultsViewState.Loading
            val searchResults = movieRepo.getMovieSearch(query)
            val newResults = searchResults.map {
                MovieListItemViewState.Data(
                    id = it.id,
                    title = it.title,
                    url ="http://image.tmdb.org/t/p/w1280${it.posterPath}",
                    rating =it.rating,
                    year =it.releaseDate,
                )
            }
            _resultsViewState.value = SearchMovieResultsViewState.Data(newResults)
        }
    }
}

