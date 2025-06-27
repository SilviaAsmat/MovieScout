package com.example.movienight20.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienight20.domain.MoviesRepository
import com.example.movienight20.ui.MovieDetailsScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _errorToastViewState = MutableStateFlow<String>("")
    val errorToastViewState: StateFlow<String> = _errorToastViewState

    init {
        // TODO fill it in here once you inject ID
    }

    fun initWithID(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val detailsResult = movieRepo.getMovieDetails(id)
            val creditsResult = movieRepo.getMovieCredits(id)
            val newState = MovieDetailsScreenViewState(
                id = detailsResult.id,
                title = detailsResult.title,
                backdropPath = "http://image.tmdb.org/t/p/" + "w1280" + detailsResult.backdropPath,
                overview = detailsResult.overview,
                runtime = detailsResult.runtime,
                status = detailsResult.status,
                genres = detailsResult.genres,
                releaseDate = detailsResult.releaseDate,
                voteAvg = detailsResult.voteAvg,
                voteCount = detailsResult.voteCount,
                tagline = detailsResult.tagline,
                cast = creditsResult.cast
            )
            mutableViewState.emit(newState)
            movieRepo.storeDataInCache(detailsResult)
        }
    }
}

