package com.example.movienight20.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.graphics.toColorInt
import com.example.movienight20.R

@Composable
fun PeopleDetailsScreen(
    onClickCastPhoto: () -> Unit,
    viewModel: PeopleDetailsViewModel,
    onBackClick: () -> Unit
){
    val viewState by viewModel.viewState.collectAsState()
    PeopleDetailsScreen(viewState = viewState, onClickCastPhoto = onClickCastPhoto, onBackClick = onBackClick)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PeopleDetailsScreen(
    viewState: PeopleDetailsScreenViewState,
    onBackClick: () -> Unit,
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
        CastPhoto(url = viewState.actorRoleMovie.)
        }
    }
}

@Composable
fun CastPhoto(
    url: String
){

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