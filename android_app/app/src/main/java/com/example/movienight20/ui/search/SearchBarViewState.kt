package com.example.movienight20.ui.search

class SearchBarViewState (
    val query: String
){
    companion object {
        val NONE = SearchBarViewState(
            query = ""
        )
    }
}