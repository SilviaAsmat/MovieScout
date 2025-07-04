package com.example.movienight20.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienight20.domain.MovieInfoBasic
import com.example.movienight20.domain.MoviesRepository
import com.example.movienight20.domain.MoviesCollectionInfo
import com.example.movienight20.ui.RecentlyViewedViewState
import com.example.movienight20.ui.movie_collection_type.MovieCollectionTypeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
): ViewModel (){
    private val mutableViewState = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.NONE)
    val viewState: StateFlow<HomeScreenViewState> = mutableViewState

    private val _recentlyViewedViewState = MutableStateFlow<RecentlyViewedViewState>(RecentlyViewedViewState.Loading)
    val recentlyViewedViewState: StateFlow<RecentlyViewedViewState> = _recentlyViewedViewState

    private val _movieCollectionTypeViewState = MutableStateFlow<MovieCollectionTypeViewState>(MovieCollectionTypeViewState.NONE)
    val movieCollectionTypeViewState: StateFlow<MovieCollectionTypeViewState> = _movieCollectionTypeViewState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAndUpdateViewState()

            movieRepo.getRecentlyViewed().collectLatest { recentlyViewed ->
                updateRecentsViewState(recentlyViewed)
            }
        }

    }

    private suspend fun fetchAndUpdateViewState() {
        val popularMoviesResult = movieRepo.getMovies()
        val nowPlayingResult = movieRepo.getNowPlaying()
        val popState = popularMoviesResult.map {
            MoviesCollectionInfo(
                id = it.id,
                title = it.title,
                backdropPath = "http://image.tmdb.org/t/p/" + "w1280" + it.backdropPath,
                posterPath = "http://image.tmdb.org/t/p/" + "w1280" + it.posterPath,
                releaseDate = it.releaseDate,
                rating = it.rating
            )
        }
        val nowPlayingState = nowPlayingResult.map {
            MoviesCollectionInfo(
                id = it.id,
                title = it.title,
                backdropPath = "http://image.tmdb.org/t/p/" + "w1280" + it.backdropPath,
                posterPath = "http://image.tmdb.org/t/p/" + "w1280" + it.posterPath,
                releaseDate = it.releaseDate,
                rating = it.rating
            )
        }
        val viewState = HomeScreenViewState(popMoviesInfo = popState, nowPlayingMoviesInfo = nowPlayingState)

        mutableViewState.emit(viewState) // TODO use update instead, to make it thread-safe
    }

    private fun updateRecentsViewState(recentlyViewed: List<MovieInfoBasic>) {
        val recentViewState = if (recentlyViewed.isEmpty()) {
            RecentlyViewedViewState.Empty
        } else {
            RecentlyViewedViewState.Data(recentlyViewedInfo = recentlyViewed)
        }
        _recentlyViewedViewState.update { recentViewState }
    }
}