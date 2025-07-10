package com.example.movienight20.ui.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movienight20.ui.MovieCardInfoViewState
import com.example.movienight20.ui.movie_collection_type.MovieCollectionType
import com.example.movienight20.ui.RecentlyViewedViewState
import com.example.movienight20.ui.movie_collection_type.MovieCollectionTypeViewState
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    onClickMoviePhoto: (Int) -> Unit,
    onClickMovieCollection: (MovieCollectionType) -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()
    val recentlyViewState by viewModel.recentlyViewedViewState.collectAsState()
    val movieCollectionTypeViewState by viewModel.movieCollectionTypeViewState.collectAsState()
    HomeScreen(
        viewState = viewState,
        recents = recentlyViewState,
        onClickMoviePhoto = onClickMoviePhoto,
        onClickMovieCollection = onClickMovieCollection,
        collectionType = movieCollectionTypeViewState
    )
}

@Composable
private fun HomeScreen(
    viewState: HomeScreenViewState,
    recents: RecentlyViewedViewState,
    collectionType: MovieCollectionTypeViewState,
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
            Header(
                header = "Popular",
                onClickMovieCollection = onClickMovieCollection,
                collectionType = collectionType
            )
            if (viewState.popMoviesInfo.isNotEmpty()) {
                MoviePaging(viewState.popMoviesInfo, onClickMoviePhoto)
            }
            Header(
                header = "Now Playing",
                onClickMovieCollection = onClickMovieCollection,
                collectionType = collectionType
            )
            HorizontalMovieListDisplay(
                movieInfo = viewState.nowPlayingMoviesInfo,
                onClickMoviePhoto
            )
            Header(
                header = "Recently Viewed",
                onClickMovieCollection = onClickMovieCollection,
                collectionType = collectionType
            )
            when (recents) {
                is RecentlyViewedViewState.Data -> {
//                    RecentlyViewedMovies(
//                        recentlyViewedInfo = recents.recentlyViewedInfo,
//                        onClickMoviePhoto
//                    )
                    RecentlyViewedMovies(
                        recentViewState = recents,
                        onClickMoviePhoto
                    )
                }

                is RecentlyViewedViewState.Empty -> {
                    // TODO create empty message composable
//                    val emptyMovieInfo = MoviesCollectionInfo(pass in empty info)
//                    HorizontalMovieListDisplay()
                }

                is RecentlyViewedViewState.Loading -> {
                    // TODO create loading state for section
                }
            }
            Header(
                header = "Top Rated",
                onClickMovieCollection = onClickMovieCollection,
                collectionType = collectionType
            )
            HorizontalMovieListDisplay(movieInfo = viewState.topRatedInfo, onClickMoviePhoto)
            Header(
                header = "Upcoming",
                onClickMovieCollection = onClickMovieCollection,
                collectionType = collectionType
            )
            HorizontalMovieListDisplay(movieInfo = viewState.upcomingInfo, onClickMoviePhoto)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenTopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color("#134e03".toColorInt())
        ),
        title = {
            Text(
                "Movie Scout",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    )
}

@Composable
private fun MoviePaging(
    popMoviesInfo: List<MovieCardInfoViewState>,
    onClickMoviePhoto: (Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 10 })
    Box() {
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
            Box(modifier = Modifier) {

                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current).crossfade(true)
                        .data(popMoviesInfo[page].backdropPath).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp, 0.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onClickMoviePhoto(popMoviesInfo[page].id) },
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.TopStart
                )
                RibbonText(title = popMoviesInfo[page].title.toString())
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

@Composable
fun RibbonText(title: String) {
    val notchDepthDp = 6.dp
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .padding(top = 145.dp)
            .offset(x = 12.dp)
            .wrapContentWidth()
            .drawBehind {
                val notchDepthPx = with(density) { notchDepthDp.toPx() }

                val ribbonPath = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width - notchDepthPx, size.height / 2f)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(ribbonPath, color = Color.Black.copy(alpha = 0.5f))
            }
            .padding(start = 10.dp, end = 15.dp, top = 6.dp, bottom = 6.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(3f, 3f),
                    blurRadius = 2f
                )
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// TODO create a new RecentlyViewedMovies that will take the view state as a parameter, in its
// own file
@Composable
private fun RecentlyViewedMovies(
    recentViewState: RecentlyViewedViewState.Data,
    onClickMoviePhoto: (Int) -> Unit
) {
    HorizontalMovieListDisplay(recentViewState.cards, onClickMoviePhoto)

}

@Composable
private fun Header(
    header: String,
    onClickMovieCollection: (MovieCollectionType) -> Unit,
    collectionType: MovieCollectionTypeViewState
) {
    val type = collectionType.getCollectionType(header)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 6.dp),
    ) {
        Text(
            text = header,
            modifier = Modifier
                .padding(0.dp, 4.dp),
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
        )
        Text(
            text = "SEE MORE",
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .padding(10.dp, 4.dp)
                .clickable { onClickMovieCollection(type) },
            color = Color.Black,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 0.sp,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )
    }

}

@Composable
private fun HorizontalMovieListDisplay(
    movieInfo: List<MovieCardInfoViewState>,
    onClickMoviePhoto: (Int) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = Modifier
            .heightIn(max = 250.dp)
            .padding(start = 12.dp, end = 0.dp, bottom = 0.dp, top = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = movieInfo) {
            Column(modifier = Modifier.height(250.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current).data(it.posterPath)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .height(220.dp)
                        .width(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onClickMoviePhoto(it.id) },
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = it.title.toString(),
                    maxLines = 1,
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .width(100.dp),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                    )
            }// End of Column
        }
    }// End of LHG
}

@Preview(showBackground = true)
@Composable
private fun previewMainScreen() {
    HomeScreen(
        viewState =
            HomeScreenViewState(
                popMoviesInfo = listOf(),
                nowPlayingMoviesInfo = listOf(),
                upcomingInfo = listOf(),
                topRatedInfo = listOf(),
            ),
        recents = RecentlyViewedViewState.Empty,
        collectionType = MovieCollectionTypeViewState.NONE,
        onClickMoviePhoto = {},
        onClickMovieCollection = {}
    )
}