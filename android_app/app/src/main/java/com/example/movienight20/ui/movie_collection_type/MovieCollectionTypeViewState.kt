package com.example.movienight20.ui.movie_collection_type

class MovieCollectionTypeViewState(
    val modelName: String
) {
    fun getCollectionType(modelName: String ): MovieCollectionType {
        val collectionType = when(modelName) {
            "Popular" -> MovieCollectionType.POPULAR
            "Now Playing" -> MovieCollectionType.NOW_PLAYING
            "Upcoming" -> MovieCollectionType.UPCOMING
            "Top Rated" -> MovieCollectionType.TOP_RATED
            else -> {
                MovieCollectionType.POPULAR// TODO: Temporary placeholder, need to replace with appropriate missing collection error
            }
        }
        return collectionType
    }


    companion object{
        val NONE = MovieCollectionTypeViewState(
            modelName = ""
        )
    }
}