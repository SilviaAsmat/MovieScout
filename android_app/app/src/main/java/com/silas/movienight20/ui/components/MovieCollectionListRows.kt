package com.silas.movienight20.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.silas.movienight20.R
import com.silas.movienight20.ui.home.HomeScreenViewState
import com.silas.movienight20.ui.movie_collection_type.MovieCollectionType

@Composable
fun MovieCollectionListRows(
    viewState: HomeScreenViewState,
    onClickMovieCollection: (MovieCollectionType) -> Unit,
    onClickMoviePhoto: (Int) -> Unit
) {
    when (viewState) {
        is HomeScreenViewState.Data -> {
            MovieCollectionHeaderRow(
                header = stringResource(R.string.now_playing_movies_row_header),
                onClickMovieCollection = { onClickMovieCollection(MovieCollectionType.NOW_PLAYING) },
            )
            MovieListRow(
                movieInfo = viewState.nowPlayingMoviesInfo,
                onClickMoviePhoto = onClickMoviePhoto
            )
            MovieCollectionHeaderRow(
                header = stringResource(R.string.top_rated_movies_row_header),
                onClickMovieCollection = { onClickMovieCollection(MovieCollectionType.TOP_RATED) },
            )
            MovieListRow(movieInfo = viewState.topRatedInfo, onClickMoviePhoto)

            MovieCollectionHeaderRow(
                header = stringResource(R.string.upcoming_movies_row_header),//TODO: Validate movie items, not appearing to be upcoming?
                onClickMovieCollection = { onClickMovieCollection(MovieCollectionType.UPCOMING) },
            )
            MovieListRow(movieInfo = viewState.upcomingInfo, onClickMoviePhoto)
        }

        is HomeScreenViewState.Loading -> {
            MovieCollectionHeaderRow(
                header = stringResource(R.string.now_playing_movies_row_header),
                onClickMovieCollection = { onClickMovieCollection(MovieCollectionType.NOW_PLAYING) },
            )
            LoadingHomeScreenRow()
            MovieCollectionHeaderRow(
                header = stringResource(R.string.top_rated_movies_row_header),
                onClickMovieCollection = { onClickMovieCollection(MovieCollectionType.TOP_RATED) },
            )
            LoadingHomeScreenRow()
            MovieCollectionHeaderRow(
                header = stringResource(R.string.upcoming_movies_row_header),
                onClickMovieCollection = { onClickMovieCollection(MovieCollectionType.UPCOMING) },
            )
            LoadingHomeScreenRow()
        }
    }
}