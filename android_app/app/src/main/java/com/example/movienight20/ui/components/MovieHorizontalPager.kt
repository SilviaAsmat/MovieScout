package com.example.movienight20.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movienight20.R
import com.example.movienight20.ui.home.HomeScreenViewState
import com.example.movienight20.ui.movie_collection_type.MovieCollectionType
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay

@Composable
fun MovieHorizontalPager(
    viewState: HomeScreenViewState,
    onClickMoviePhoto: (Int) -> Unit,
    onClickMovieCollection: (MovieCollectionType) -> Unit
) {
    MovieCollectionHeaderRow(
        header = stringResource(R.string.popular_movies_row_header),
        onClickMovieCollection = { onClickMovieCollection(MovieCollectionType.POPULAR) },
    )
    when (viewState) {
        is HomeScreenViewState.Data -> {
            val movieInfo = viewState.popMoviesInfo
            if (movieInfo.isEmpty()) return
            val pagerState = rememberPagerState(pageCount = { 10 })
            Box(modifier = Modifier.padding(bottom = 16.dp)) {
                val pageCount = 10
                LaunchedEffect(Unit) {
                    while (true) {
                        delay(4000) // Delay between automatic scrolls
                        val nextPage = (pagerState.currentPage + 1) % pageCount
                        pagerState.animateScrollToPage(
                            nextPage, animationSpec = tween(
                                durationMillis = 600, // Duration of slide transition
                                easing = FastOutSlowInEasing // Smooth easing
                            )
                        )
                    }
                }
                HorizontalPager(state = pagerState, key = { it })
                { page ->
                    Box() {
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .crossfade(true)
                                .data(movieInfo[page].backdropPath).build(),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp, bottom = 0.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onClickMoviePhoto(movieInfo[page].id) },
                            contentScale = ContentScale.FillWidth,
                            alignment = Alignment.TopStart
                        )
                        MovieRibbonOverlay(title = movieInfo[page].title.toString())
                    }
                }
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .offset(y = (-3).dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(3.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(6.dp)
                        )
                    }
                }// End of Row
            }// End of Box
        }

        HomeScreenViewState.Loading -> {
            Box(
                modifier = Modifier
                    .shimmer()
                    .padding(start = 12.dp, end = 12.dp, bottom = 0.dp)
                    .background(Color.LightGray)
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}