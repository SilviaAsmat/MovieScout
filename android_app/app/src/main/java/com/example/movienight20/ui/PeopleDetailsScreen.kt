package com.example.movienight20.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movienight20.R
import com.example.movienight20.domain.ActorRoleMovie

@Composable
fun PeopleDetailsScreen(
    onClickCastPhoto: () -> Unit,
    viewModel: PeopleDetailsViewModel,
    onBackClick: () -> Unit,
    onClickMoviePhoto: (Int) -> Unit
){
    val viewState by viewModel.viewState.collectAsState()
    PeopleDetailsScreen(viewState = viewState, onClickCastPhoto = onClickCastPhoto, onBackClick = onBackClick, onClickMoviePhoto = onClickMoviePhoto)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PeopleDetailsScreen(
    viewState: PeopleDetailsScreenViewState,
    onBackClick: () -> Unit,
    onClickMoviePhoto: (Int) -> Unit,
    onClickCastPhoto: () -> Unit
){
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {CastDetailsTopAppBar(onBackClick = onBackClick) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.White)
                .padding(innerPadding)
        ) {
            Row {
                CastPhoto(url = viewState.profilePath)
                CastDetails(name = viewState.name, birthday = viewState.birthday, birthplace = viewState.birthPlace, deathday = viewState.deathday)
            }
            CastBio(bio = viewState.bio)
            CastMovies(movies = viewState.actorRoleMovie, modifier = Modifier, onClickMoviePhoto = onClickMoviePhoto)
        }
    }
}

@Composable
private fun CastPhoto(
    url: String
){
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current).data("http://image.tmdb.org/t/p/w1280$url").build(),
        contentDescription = null,
        modifier = Modifier
            .padding(16.dp, 16.dp, 10.dp, 10.dp)
            .height(220.dp)
            .width(150.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color("#581845".toColorInt())),
        contentScale = ContentScale.Crop
    )

}

@Composable
private fun CastDetails(name: String, birthday: String, birthplace: String, deathday: String) {
    Column {
        Text(
            text = name,
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 0.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 30.sp,
            color = Color.Black,
        )
        Text(
        text = "Birthday:\n$birthday\nBirthplace:\n$birthplace\nDied:\n$deathday",
        modifier = Modifier
            .padding(16.dp, 0.dp),
        fontSize = 16.sp,
        lineHeight = 30.sp,
        color = Color.Black
    ) }
}

@Composable
private fun CastBio(bio: String) {
    Text(
        text = bio,
        modifier = Modifier
            .padding(16.dp),
        fontSize = 16.sp,
        color = Color.Black
    )
}

@Composable
private fun CastMovies(movies: List<ActorRoleMovie>, modifier: Modifier, onClickMoviePhoto: (Int) -> Unit) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = modifier.heightIn(max = 220.dp).padding(16.dp, 10.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(items = movies) {
            Column(modifier = modifier.background(Color.White).height(220.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data("http://image.tmdb.org/t/p/" + "w1280" + it.posterPath).build(),
                    contentDescription = it.title.toString(),
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable{onClickMoviePhoto(it.id!!)}
                    ,
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = it.title.toString(),
                    maxLines = 1,
                    modifier = modifier
                        .padding(2.dp, 0.dp)
                        .width(100.dp),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,

                )
            }// End of Column
        }}// End of LHG
}


@ExperimentalMaterial3Api
@Composable
fun CastDetailsTopAppBar(
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color("#4b8f38".toColorInt())
        ),
        title = {
            Text(
                "Cast Details",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "back arrow"
                )}
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    )
}

@Preview(showBackground = true)
@Composable
fun previewPeopleDetailsScreen(){
    PeopleDetailsScreen(
        viewState = PeopleDetailsScreenViewState(
            actorRoleMovie = listOf(),
            crewRoleMovie = listOf(),
            bio = "Person grew up in blah and has blah hobbies, their movies include blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah " +
                    " blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah " +
                    " blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah ",
            birthday = "12-12-1212",
            deathday = "11-11-1311",
            name = "ReallyReallyReally LongNameLongName",
            birthPlace = "City, Place",
            profilePath = ""
        ),
        onClickCastPhoto = {},
        onBackClick = {},
        onClickMoviePhoto = {}
    )
}
