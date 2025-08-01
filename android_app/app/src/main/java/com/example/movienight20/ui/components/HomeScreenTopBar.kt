package com.example.movienight20.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.movienight20.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(onSearchScreenClick: () -> Unit) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.app_bar_primary)
        ),
        title = {
            Text(
                stringResource(R.string.home_screen_top_app_bar),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        actions = {
            IconButton(onClick = { onSearchScreenClick() }) {
                Icon(Icons.Filled.Search, contentDescription = "Nav to Search Screen", tint = Color.White)
            }
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    )
}