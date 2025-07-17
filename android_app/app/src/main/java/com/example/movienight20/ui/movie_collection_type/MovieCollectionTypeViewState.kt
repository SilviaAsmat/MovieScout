package com.example.movienight20.ui.movie_collection_type

class MovieCollectionTypeViewState(
) {
    fun getCollectionType(modelName: String): MovieCollectionType {
        val collectionType = when(modelName) {
            MovieCollectionType.POPULAR.name -> MovieCollectionType.POPULAR
            MovieCollectionType.NOW_PLAYING.name -> MovieCollectionType.NOW_PLAYING
            MovieCollectionType.UPCOMING.name -> MovieCollectionType.UPCOMING
            MovieCollectionType.TOP_RATED.name -> MovieCollectionType.TOP_RATED
            else -> {
                MovieCollectionType.TOP_RATED// TODO: Temporary placeholder, need to replace with appropriate missing collection error
            }
        }
        return collectionType
    }

    companion object{
        val NONE = MovieCollectionTypeViewState(
        )
    }
}