package com.example.movienight20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.movienight20.ui.MainNavHost
import com.example.movienight20.ui.home.HomeScreenViewModel
import com.example.movienight20.ui.details.people.PeopleDetailsViewModel
import com.example.movienight20.ui.theme.MovieDetailsViewModel
import com.example.movienight20.ui.theme.MovieNight20Theme
import com.example.movienight20.ui.theme.PopularMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: PopularMoviesViewModel by viewModels()
    private val detailsViewModel: MovieDetailsViewModel by viewModels()
    private val peopleDetailsViewModel: PeopleDetailsViewModel by viewModels()
    private val homeScreenViewModel: HomeScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieNight20Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MainNavHost(
                        navController = navController,
                        popularMoviesViewModel = viewModel,
                        detailsViewModel = detailsViewModel,
                        peopleDetailsViewModel = peopleDetailsViewModel,
                        homeScreenViewModel = homeScreenViewModel
                        )
                }
            }
        }
    }
}


