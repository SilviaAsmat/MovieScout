package com.example.movienight20.ui.home

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movienight20.domain.MovieInfoBasic
import com.example.movienight20.domain.PopularMoviesInfo

@Composable
fun MainScreen(
    viewModel: HomeScreenViewModel,
    onClickMoviePhoto: (Int) -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()
    val recentlyViewState by viewModel.recentlyViewedViewState.collectAsState()
    MainScreen(viewState = viewState, recents = recentlyViewState, onClickMoviePhoto = onClickMoviePhoto)
}

@Composable
private fun MainScreen(
    viewState: HomeScreenViewState,
    recents: List<MovieInfoBasic>,
    onClickMoviePhoto: (Int) -> Unit) {
    Scaffold(
        topBar = {MainScreenTopBar()}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Header(header = "Popular")
            if (viewState.popMoviesInfo.isNotEmpty()) {
                PopularMovies(viewState.popMoviesInfo, onClickMoviePhoto)
            }
            Header(header = "Now Playing")
            NowPlayingMovies(viewState.nowPlayingMoviesInfo, onClickMoviePhoto)
            Header(header = "Recently Viewed")
            RecentlyViewedMovies(
                recentlyViewedInfo = recents,
                onClickMoviePhoto
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenTopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color("#4b8f38".toColorInt())
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
private fun PopularMovies(
    popMoviesInfo: List<PopularMoviesInfo>,
    onClickMoviePhoto: (Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 10 })
    Box() {
        HorizontalPager(state = pagerState, key = { it }) //TODO: Look into auto-advancing
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
                RibbonText(title = popMoviesInfo[page].title)
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
                        .size(8.dp)
                )
            }
        }// End of Row
    }// End of Box
}

@Composable
fun RibbonText(title: String) {
    val notchDepthDp = 8.dp
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .padding(top = 16.dp)
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

@Composable
private fun NowPlayingMovies(
    nowPlayingInfo: List<PopularMoviesInfo>,
    onClickMoviePhoto: (Int) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = Modifier
            .heightIn(max = 220.dp)
            .padding(start = 12.dp, end = 0.dp, bottom = 10.dp, top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = nowPlayingInfo) {
            Column(modifier = Modifier.height(220.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current).data(it.posterPath)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
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
                    overflow = TextOverflow.Ellipsis,

                    )
            }// End of Column
        }
    }// End of LHG
}


@Composable
private fun RecentlyViewedMovies(
    recentlyViewedInfo: List<MovieInfoBasic>,
    onClickMoviePhoto: (Int) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = Modifier
            .heightIn(max = 220.dp)
            .padding(start = 12.dp, end = 0.dp, bottom = 10.dp, top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = recentlyViewedInfo) {
            Column(modifier = Modifier.height(220.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current).data(it.posterPath)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onClickMoviePhoto(it.id) },
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = it.name.toString(),
                    maxLines = 1,
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .width(100.dp),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,

                    )
            }// End of Column
        }
    }// End of LHG
}

@Composable
private fun Header(header: String) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Text(
            text = header,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 8.dp)

        )
    }

}

@Preview(showBackground = true)
@Composable
private fun previewMainScreen() {
    MainScreen(
        viewState =
            HomeScreenViewState(
                popMoviesInfo = listOf(),
                nowPlayingMoviesInfo = listOf(),
            ),
        recents = emptyList(),
        onClickMoviePhoto = {}
    )
}