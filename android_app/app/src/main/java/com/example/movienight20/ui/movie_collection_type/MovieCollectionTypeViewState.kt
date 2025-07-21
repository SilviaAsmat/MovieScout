package com.example.movienight20.ui.movie_collection_type

import androidx.compose.runtime.Immutable

@Immutable
class MovieCollectionTypeViewState() {
    fun getCollectionType(modelName: String): MovieCollectionType {
        val collectionType = when(modelName) {
            MovieCollectionType.POPULAR.name -> MovieCollectionType.POPULAR
            MovieCollectionType.NOW_PLAYING.name -> MovieCollectionType.NOW_PLAYING
            MovieCollectionType.UPCOMING.name -> MovieCollectionType.UPCOMING
            MovieCollectionType.TOP_RATED.name -> MovieCollectionType.TOP_RATED
            else -> {MovieCollectionType.POPULAR}
        }
        return collectionType
    }

    companion object{
        val NONE = MovieCollectionTypeViewState(
        )
    }
}