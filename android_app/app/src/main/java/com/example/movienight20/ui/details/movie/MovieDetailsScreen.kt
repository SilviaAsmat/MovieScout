package com.example.movienight20.ui.details.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movienight20.R
import com.example.movienight20.domain.Genre
import com.example.movienight20.ui.theme.MovieDetailsViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.ui.draw.clip
import com.example.movienight20.domain.Cast
import androidx.core.graphics.toColorInt
import com.example.movienight20.ui.components.GenreLabels
import com.example.movienight20.ui.components.MovieCast
import com.example.movienight20.ui.components.TrailersHorizontalPager

@Composable
fun MovieDetailsScreen(
    onClickMovieListItem: () -> Unit,
    viewModel: MovieDetailsViewModel,
    navController: NavController,
    onBackClick: () -> Unit,
    onClickCastPhoto: (Int) -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()
    val errorToastViewState by viewModel.errorToastViewState.collectAsState()
    MovieDetailsScreen(
        viewState = viewState,
        onClickCastPhoto = onClickCastPhoto,
        onClickMovieDetailsScreen = onClickMovieListItem,
        onBackClick = onBackClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailsScreen(
    viewState: MovieDetailsScreenViewState,
    onClickMovieDetailsScreen: () -> Unit,
    onBackClick: () -> Unit,
    onClickCastPhoto: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = { MovieDetailsTopAppBar(onBackClick = onBackClick) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.White)
                .padding(innerPadding)
        ) {

            MoviePoster(url = viewState.backdropPath, onClickMovieDetailsScreen)
            Title(title = viewState.title)
            if (viewState.tagline.isNotBlank()) {
                Tagline(tagline = viewState.tagline)
            }
            Row {
                ReleaseDateLabel(
                    releaseDate = viewState.releaseDate,
                    modifier = Modifier.weight(1f)
                )
                VoteAvgLabel(voteAvg = viewState.voteAvg)
                VoteCountLabel(voteCount = viewState.voteCount)
            }
            Overview(overview = viewState.overview)
            GenreLabels(genres = viewState.genres)
            TrailersHorizontalPager(
                trailers = viewState.videos
            )
            MovieCast(cast = viewState.cast, onClickCastPhoto = onClickCastPhoto)
        }
    }
}

@Composable
private fun MoviePoster(
    url: String,
    onClickMovieListItem: () -> Unit,
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current).data(url).build(),
        contentDescription = null,
        modifier = Modifier
            .padding(16.dp, 16.dp, 16.dp, 6.dp)
            .fillMaxWidth()
            .height(230.dp)
            .clip(RoundedCornerShape(32.dp))
            .clickable {
                onClickMovieListItem()
            },
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun Title(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 0.dp),
        fontSize = 22.sp,
        maxLines = 2,
        overflow = TextOverflow.Visible,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )

}

@Composable
private fun Tagline(tagline: String, modifier: Modifier = Modifier) {
    Text(
        text = tagline,
        fontSize = 16.sp,
        modifier = modifier
            .padding(16.dp, 10.dp, 16.dp, 0.dp),
        color = Color.Black,
        fontStyle = FontStyle.Italic
    )
}

@Composable
private fun ReleaseDateLabel(releaseDate: String, modifier: Modifier = Modifier) {
    Text(
        text = releaseDate,
        modifier = modifier
            .padding(16.dp, 10.dp),
        fontSize = 16.sp,
        color = Color.Black
    )
}

@Composable
private fun VoteAvgLabel(voteAvg: Number, modifier: Modifier = Modifier) {
    Text(
        text = voteAvg.toString(),
        fontSize = 16.sp,
        modifier = modifier
            .padding(0.dp, 10.dp, 0.dp, 0.dp),
        color = Color.Black
    )

}

@Composable
private fun VoteCountLabel(voteCount: Int, modifier: Modifier = Modifier) {
    Text(
        text = "($voteCount)",
        fontSize = 16.sp,
        modifier = modifier
            .padding(6.dp, 10.dp, 16.dp, 0.dp),
        color = Color.Black
    )

}

@Composable
private fun Overview(overview: String, modifier: Modifier = Modifier) {
    Text(
        text = overview,
        fontSize = 16.sp,
        modifier = modifier
            .padding(16.dp, 12.dp),
        color = Color.Black
    )

}



@ExperimentalMaterial3Api
@Composable
private fun MovieDetailsTopAppBar(
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color("#134e03".toColorInt())
        ),
        title = {
            Text(
                "Movie Details",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "back arrow"
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewMovieDetailsScreen() {
    MovieDetailsScreen(
        viewState = MovieDetailsScreenViewState(
            id = 12345,
            title = "SomeMovieThatIsOkay",
            backdropPath = "Poster",
            overview = "blahblahblahblahblahblahblahblahblahblahblahblahblahblahblahb\nlahblahblahblahblahblahblahblahblahblahblahblahblahblahblah",
            runtime = 90,
            status = "released",
            genres = listOf(
                Genre(id = 1, title = "Comedy"),
                Genre(id = 2, title = "Horror"),
                Genre(id = 2, title = "Action")
            ),
            releaseDate = "11-11-1111",
            voteAvg = 8.0,
            voteCount = 111,
            tagline = "this epic movie is about to get more epic",
            cast = listOf(
                Cast(
                    castId = 0,
                    name = "Famous Actor Name That is too long",
                    picturePath = "",
                    character = "character"
                ),
                Cast(
                    castId = 0,
                    name = "Famous Actor",
                    picturePath = "",
                    character = "character name that is too long"
                ),
                Cast(
                    castId = 0,
                    name = "Famous Actor",
                    picturePath = "",
                    character = "character name that is too long"
                ),
                Cast(
                    castId = 0,
                    name = "Famous Actor",
                    picturePath = "",
                    character = "character name that is too long"
                )
            ),
            videos = TODO()
        ),
        onClickMovieDetailsScreen = {},
        onBackClick = {},
        onClickCastPhoto = {},
    )
}
