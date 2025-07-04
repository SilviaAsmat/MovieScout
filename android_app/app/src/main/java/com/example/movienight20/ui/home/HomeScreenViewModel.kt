package com.example.movienight20.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienight20.domain.MovieInfoBasic
import com.example.movienight20.domain.MoviesRepository
import com.example.movienight20.domain.MoviesCollectionInfo
import com.example.movienight20.ui.MovieCardInfoViewState
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
import kotlin.collections.map

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
) : ViewModel() {
    private val mutableViewState = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.NONE)
    val viewState: StateFlow<HomeScreenViewState> = mutableViewState

    private val _recentlyViewedViewState =
        MutableStateFlow<RecentlyViewedViewState>(RecentlyViewedViewState.Loading)
    val recentlyViewedViewState: StateFlow<RecentlyViewedViewState> = _recentlyViewedViewState

    private val _movieCollectionTypeViewState =
        MutableStateFlow<MovieCollectionTypeViewState>(MovieCollectionTypeViewState.NONE)
    val movieCollectionTypeViewState: StateFlow<MovieCollectionTypeViewState> =
        _movieCollectionTypeViewState

    private val _movieCardInfoViewState =
        MutableStateFlow<MovieCardInfoViewState>(MovieCardInfoViewState.NONE)
    val movieCardInfoViewState: StateFlow<MovieCardInfoViewState> = _movieCardInfoViewState

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
        val upcomingResult = movieRepo.getUpcomingMovies()
        val topRatedResult = movieRepo.getTopRated()
        val popState = mapMovieCollection(popularMoviesResult)
        val nowPlayingState = mapMovieCollection(nowPlayingResult)
        val upcomingState = mapMovieCollection(upcomingResult)
        val topRatedState = mapMovieCollection(topRatedResult)

        val viewState =
            HomeScreenViewState(popMoviesInfo = popState, nowPlayingMoviesInfo = nowPlayingState,
                upcomingInfo = upcomingState, topRatedInfo = topRatedState)

        mutableViewState.update { viewState }
    }

    private fun mapMovieCollection(result: List<MoviesCollectionInfo>): List<MovieCardInfoViewState> {
        return result.map {
            MovieCardInfoViewState(
                id = it.id,
                title = it.title,
                posterPath = "http://image.tmdb.org/t/p/" + "w1280" + it.posterPath,
                backdropPath = "http://image.tmdb.org/t/p/" + "w1280" + it.backdropPath
            )
        }
    }

    fun updateRecentsViewState(recentlyViewed: List<MovieInfoBasic>) {
        val recentViewState = if (recentlyViewed.isEmpty()) {
            RecentlyViewedViewState.Empty
        } else {
            RecentlyViewedViewState.Data(cards = recentlyViewed.map { recent ->
                MovieCardInfoViewState(
                    id = recent.id,
                    title = recent.title,
                    posterPath = "http://image.tmdb.org/t/p/" + "w1280" + recent.posterPath.toString(),
                    backdropPath = ""// Does not use backdrop path
                )
            })
        }
        _recentlyViewedViewState.update { recentViewState }
    }
}
