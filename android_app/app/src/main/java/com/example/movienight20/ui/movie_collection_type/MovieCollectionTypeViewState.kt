package com.example.movienight20.ui.movie_collection_type

class MovieCollectionTypeViewState(
    val modelName: String
) {
    fun getCollectionType(modelName: String ): MovieCollectionType {
        val collectionType = when(modelName) {
            "Popular" -> MovieCollectionType.POPULAR
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