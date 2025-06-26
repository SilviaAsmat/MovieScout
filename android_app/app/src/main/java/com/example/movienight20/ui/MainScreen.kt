package com.example.movienight20.ui

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movienight20.domain.PopularMoviesInfo

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    onClickMoviePhoto: (Int) -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()
    MainScreen(viewState = viewState, onClickMoviePhoto = onClickMoviePhoto)
}

@Composable
private fun MainScreen(viewState: MainScreenViewState, onClickMoviePhoto: (Int) -> Unit) {
    Scaffold(
        //topBar = {}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.LightGray)
        ) {
            Header(header = "Popular")
            if (viewState.popMoviesInfo.isNotEmpty()) {
                PopularMovies(viewState.popMoviesInfo, onClickMoviePhoto)
            }
            Header(header = "Now Playing")
            NowPlayingMovies(viewState.nowPlayingMoviesInfo, onClickMoviePhoto)

        }
    }
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
                        //.height(200.dp)
                        .fillMaxWidth()
                        .clickable { onClickMoviePhoto(popMoviesInfo[page].id) },
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.TopStart
                )
                Text(
                    text = popMoviesInfo[page].title,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black, // Color of the shadow
                            offset = Offset(3f, 3f), // X and Y offset of the shadow
                            blurRadius = 2f // How blurry the shadow is
                        )
                    )
                )
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
private fun NowPlayingMovies(
    nowPlayingInfo: List<PopularMoviesInfo>,
    onClickMoviePhoto: (Int) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = Modifier
            .heightIn(max = 220.dp)
            .padding(0.dp, 10.dp)
            .background(color = Color.LightGray),
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
private fun Header(header: String) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Text(
            text = header,
            color = Color.Black

        )
    }

}

@Preview(showBackground = true)
@Composable
private fun previewMainScreen() {
    MainScreen(
        viewState =
            MainScreenViewState(
                popMoviesInfo = listOf(),
                nowPlayingMoviesInfo = listOf()
            ),
        onClickMoviePhoto = {}
    )
}