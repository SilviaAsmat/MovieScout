package com.example.movienight20.ui.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.example.movienight20.domain.MoviesRepository
import com.example.movienight20.ui.TopAppBarViewState
import com.example.movienight20.ui.movie_collection_type.MovieCollectionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListScreenViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
) : ViewModel() {
    private val mutableViewState = MutableStateFlow<List<MovieListItemViewState>>(emptyList())
    val viewState: StateFlow<List<MovieListItemViewState>> = mutableViewState

    private val _topAppBarViewState = MutableStateFlow<TopAppBarViewState>(TopAppBarViewState.Companion.NONE)
    val topAppBarViewState: StateFlow<TopAppBarViewState> = _topAppBarViewState

    // TODO use popMovies in composable
    val popMovies = movieRepo.popularMoviesPagination().map { pagingData ->
        pagingData.map { movie ->
            val releaseYear = ""
            val url = "http://image.tmdb.org/t/p/" + "w1280" + movie.posterPath
            MovieListItemViewState(
                id = movie.remoteId,
                title = movie.name ?: "",
                url = url,
                year = releaseYear,
                rating = ""
            )
        }
    }


    fun initViewModel(collectionType: MovieCollectionType) {
        viewModelScope.launch {
            val result = when (collectionType) {
                MovieCollectionType.POPULAR -> movieRepo.getMovies()
                MovieCollectionType.NOW_PLAYING -> movieRepo.getNowPlaying()
                MovieCollectionType.TOP_RATED -> movieRepo.getTopRated()
                MovieCollectionType.UPCOMING -> movieRepo.getUpcomingMovies()
            }

            _topAppBarViewState.emit(TopAppBarViewState(collectionType))

            val viewStates = result.map {
                val releaseYear = it.releaseDate.substringBefore("-")
                val url = "http://image.tmdb.org/t/p/" + "w1280" + it.posterPath
                MovieListItemViewState(
                    id = it.id,
                    title = it.title,
                    url = url,
                    year = releaseYear,
                    rating = it.rating
                )
            }
            mutableViewState.emit(viewStates)
        }

    }

}