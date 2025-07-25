package com.example.movienight20.ui.recently_viewed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.movienight20.ui.components.MovieListSinglePage
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.movienight20.ui.components.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentlyViewedScreen(
    onClickMovieListItem: (Int) -> Unit,
    viewModel: RecentlyViewedScreenViewModel,
    navController: NavController
) {
    val viewState by viewModel.recentsScreenViewState.collectAsState()
    Scaffold() { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TopAppBar(title = "Recently Viewed")
            RecentlyViewedScreen(
                viewState,
                onClickMovieListItem
            )
        }
    }

}

@Composable
private fun RecentlyViewedScreen(
    viewState: RecentlyViewedScreenViewState,
    onClickMovieListItem: (Int) -> Unit
) {
    when (viewState) {
        is RecentlyViewedScreenViewState.Data -> {
            val cards = viewState.cards
            MovieListSinglePage(cards, onClickMovieListItem)
        }

        RecentlyViewedScreenViewState.Loading -> {
            // TODO
        }

        RecentlyViewedScreenViewState.Empty -> {
            // do nothing
        }
    }
}