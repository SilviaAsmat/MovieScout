package com.example.movienight20.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.movienight20.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor (
    private val movieRepo: MoviesRepository
): ViewModel(){
    private val mutableViewState = MutableStateFlow<List<MovieSearchViewState.Loading>>(emptyList())
    val viewState: StateFlow<List<MovieSearchViewState>> = mutableViewState

    init {
        viewModelScope.launch {
            val results = movieRepo.getMovieSearch("")
        }
    }
}