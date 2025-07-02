package com.example.movienight20.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienight20.domain.MoviesRepository
import com.example.movienight20.domain.PopularMoviesInfo
import com.example.movienight20.ui.RecentlyViewedViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
): ViewModel (){
    private val mutableViewState = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.NONE)
    val viewState: StateFlow<HomeScreenViewState> = mutableViewState

    private val _recentlyViewedViewState = MutableStateFlow<RecentlyViewedViewState>(
        RecentlyViewedViewState.Companion.NONE)
    val recentlyViewedViewState: StateFlow<RecentlyViewedViewState> = _recentlyViewedViewState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val popularMoviesResult = movieRepo.getMovies()
            val nowPlayingResult = movieRepo.getNowPlaying()
            val popState = popularMoviesResult.map {
                PopularMoviesInfo(
                    id = it.id,
                    title = it.title,
                    backdropPath = "http://image.tmdb.org/t/p/" + "w1280" + it.backdropPath,
                    posterPath = "http://image.tmdb.org/t/p/" + "w1280" + it.posterPath,
                    releaseDate = it.releaseDate,
                    rating = it.rating
                )
            }
            val nowPlayingState = nowPlayingResult.map {
                PopularMoviesInfo(
                    id = it.id,
                    title = it.title,
                    backdropPath = "http://image.tmdb.org/t/p/" + "w1280" + it.backdropPath,
                    posterPath = "http://image.tmdb.org/t/p/" + "w1280" + it.posterPath,
                    releaseDate = it.releaseDate,
                    rating = it.rating
                )
            }

            val viewState = HomeScreenViewState(popMoviesInfo = popState, nowPlayingMoviesInfo = nowPlayingState)
            mutableViewState.emit(viewState)

            movieRepo.getRecentlyViewed().collectLatest { recentlyViewed ->
                _recentlyViewedViewState.emit(RecentlyViewedViewState(recentlyViewed))
            }
        }

    }
}