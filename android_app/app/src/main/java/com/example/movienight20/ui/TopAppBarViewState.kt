package com.example.movienight20.ui

import com.example.movienight20.ui.movie_collection_type.MovieCollectionType

class  TopAppBarViewState (
    val collectionType: MovieCollectionType
){
    fun getStringName(): String {
        val name = when(collectionType) {
            MovieCollectionType.POPULAR -> "Popular"
            MovieCollectionType.NOW_PLAYING -> "Now Playing"
            MovieCollectionType.TOP_RATED -> "Top Rated"
            MovieCollectionType.UPCOMING -> "Upcoming"
        }
        return name
    }
    companion object{
        val NONE = TopAppBarViewState(
            collectionType = MovieCollectionType.POPULAR
        )
    }
}