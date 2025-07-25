package com.example.movienight20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.movienight20.ui.MainNavHost
import com.example.movienight20.ui.theme.MovieNight20Theme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
//                        listScreenViewModel = collectionViewModel,
//                        detailsViewModel = detailsViewModel,
//                        peopleDetailsViewModel = peopleDetailsViewModel,
//                        homeScreenViewModel = homeScreenViewModel
                        )
                }
            }
        }
    }
}


