package com.example.movienight20.ui.movie_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movienight20.data.room.movie_info_basic.MovieInfoBasicEntity
import com.example.movienight20.domain.MoviesRepository
import com.example.movienight20.ui.TopAppBarViewState
import com.example.movienight20.ui.movie_collection_type.MovieCollectionTypeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListScreenViewModel @Inject constructor(
    private val movieRepo: MoviesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val mutableViewState = MutableStateFlow<List<MovieListItemViewState.Loading>>(emptyList())
    val viewState: StateFlow<List<MovieListItemViewState>> = mutableViewState

    private val _topAppBarViewState =
        MutableStateFlow<TopAppBarViewState>(TopAppBarViewState.Companion.NONE)
    val topAppBarViewState: StateFlow<TopAppBarViewState> = _topAppBarViewState

    val pagedMovies = getMoviesPagination().map { pagingData ->
        pagingData.map { movie ->
            val releaseYear = ""
            val url = "http://image.tmdb.org/t/p/" + "w1280" + movie.posterPath
            MovieListItemViewState.Data(
                id = movie.remoteId,
                title = movie.name,
                url = url,
                year = releaseYear,
                rating = ""
            )
        }
    }

    init {
        viewModelScope.launch {
            val savedStateValue = savedStateHandle.get<String>("collectionType")
            val collectionType = MovieCollectionTypeViewState().getCollectionType(savedStateValue!!)
            _topAppBarViewState.emit(TopAppBarViewState(collectionType))
        }
    }

    private fun getMoviesPagination(): Flow<PagingData<MovieInfoBasicEntity>> {
        val savedStateValue = savedStateHandle.get<String>("collectionType")
        val collectionType = MovieCollectionTypeViewState().getCollectionType(savedStateValue!!)
        return movieRepo.moviesPagination(collectionType)
    }
}