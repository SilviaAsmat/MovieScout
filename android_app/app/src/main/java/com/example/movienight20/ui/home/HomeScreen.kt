package com.example.movienight20.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.movienight20.R
import com.example.movienight20.ui.components.HeaderLabel
import com.example.movienight20.ui.components.HomeScreenTopBar
import com.example.movienight20.ui.components.MovieCollectionHeaderRow
import com.example.movienight20.ui.components.MovieListRow
import com.example.movienight20.ui.components.MoviePaging
import com.example.movienight20.ui.movie_collection_type.MovieCollectionType
import com.example.movienight20.ui.recently_viewed.RecentlyViewedViewState
import com.example.movienight20.ui.recently_viewed.RecentlyViewedMoviesRow

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    onClickMoviePhoto: (Int) -> Unit,
    onClickMovieCollection: (MovieCollectionType) -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()
    val recentlyViewState by viewModel.recentlyViewedViewState.collectAsState()
    HomeScreen(
        viewState = viewState,
        recents = recentlyViewState,
        onClickMoviePhoto = onClickMoviePhoto,
        onClickMovieCollection = onClickMovieCollection,
    )
}

@Composable
private fun HomeScreen(
    viewState: HomeScreenViewState,
    recents: RecentlyViewedViewState,
    onClickMoviePhoto: (Int) -> Unit,
    onClickMovieCollection: (MovieCollectionType) -> Unit
) {
    Scaffold(
        topBar = { HomeScreenTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            MovieCollectionHeaderRow(
                header = stringResource(R.string.popular_movies_row_header),
                onClickMovieCollection = { onClickMovieCollection(MovieCollectionType.POPULAR) },
            )
            MoviePaging(viewState.popMoviesInfo, onClickMoviePhoto)
            MovieCollectionHeaderRow(
                header = stringResource(R.string.now_playing_movies_row_header),
                onClickMovieCollection = { onClickMovieCollection(MovieCollectionType.NOW_PLAYING) },
            )
            MovieListRow(
                movieInfo = viewState.nowPlayingMoviesInfo,
                onClickMoviePhoto
            )
            RecentlyViewedMoviesRow(recents, onClickMoviePhoto)
            MovieCollectionHeaderRow(
                header = stringResource(R.string.top_rated_movies_row_header),
                onClickMovieCollection = { onClickMovieCollection(MovieCollectionType.TOP_RATED) },
            )
            MovieListRow(movieInfo = viewState.topRatedInfo, onClickMoviePhoto)
            MovieCollectionHeaderRow(
                header = stringResource(R.string.upcoming_movies_row_header),
                onClickMovieCollection = { onClickMovieCollection(MovieCollectionType.UPCOMING) },
            )
            MovieListRow(movieInfo = viewState.upcomingInfo, onClickMoviePhoto)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMainScreen() {
    HomeScreen(
        viewState =
            HomeScreenViewState(
                popMoviesInfo = listOf(),
                nowPlayingMoviesInfo = listOf(),
                upcomingInfo = listOf(),
                topRatedInfo = listOf(),
            ),
        recents = RecentlyViewedViewState.Empty,
        onClickMoviePhoto = {},
        onClickMovieCollection = {}
    )
}