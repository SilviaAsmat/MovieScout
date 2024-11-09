package com.example.movienight20.ui

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface Destinations {
    val route: String
}

data object MovieList : Destinations {
    override val route = "MovieList"
}
data object MovieDetails : Destinations {
    override val route = "MovieDetails"
}