package com.example.movienight20.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.movienight20.ui.components.HomeScreenTopBar
import com.example.movienight20.ui.components.MovieCollectionListRows
import com.example.movienight20.ui.components.MovieHorizontalPager
import com.example.movienight20.ui.movie_collection_type.MovieCollectionType
import com.example.movienight20.ui.recently_viewed.RecentlyViewedViewState
import com.example.movienight20.ui.recently_viewed.RecentlyViewedMoviesRow

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    onClickMoviePhoto: (Int) -> Unit,
    onClickMovieCollection: (MovieCollectionType) -> Unit,
    onSearchScreenClick: () -> Unit,
    onRecentsScreenClick: () -> Unit

) {
    val viewState by viewModel.viewState.collectAsState()
    val recentlyViewState by viewModel.recentlyViewedViewState.collectAsState()
    HomeScreen(
        viewState = viewState,
        recents = recentlyViewState,
        onClickMoviePhoto = onClickMoviePhoto,
        onClickMovieCollection = onClickMovieCollection,
        onSearchScreenClick = onSearchScreenClick,
        onRecentsScreenClick = onRecentsScreenClick
    )
}

@Composable
private fun HomeScreen(
    viewState: HomeScreenViewState,
    recents: RecentlyViewedViewState,
    onClickMoviePhoto: (Int) -> Unit,
    onClickMovieCollection: (MovieCollectionType) -> Unit,
    onSearchScreenClick: () -> Unit,
    onRecentsScreenClick: () -> Unit
) {
    Scaffold(
        topBar = { HomeScreenTopBar(onSearchScreenClick) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            MovieHorizontalPager(viewState, onClickMoviePhoto, onClickMovieCollection)
            RecentlyViewedMoviesRow(recents, onClickMoviePhoto, onRecentsScreenClick)
            MovieCollectionListRows(
                viewState = viewState,
                onClickMovieCollection = onClickMovieCollection,
                onClickMoviePhoto = onClickMoviePhoto
            )

        }
    }
}


//@Preview(showBackground = true)
//@Composable
//private fun PreviewMainScreen() {
//    HomeScreen(
//            ),
//        recents = RecentlyViewedViewState.Empty,
//        onClickMoviePhoto = {},
//        onClickMovieCollection = {}
//    )
//}