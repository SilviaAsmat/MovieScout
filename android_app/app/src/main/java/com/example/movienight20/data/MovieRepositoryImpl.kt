package com.example.movienight20.data

import android.util.Log
import com.example.movienight20.data.room.MovieInfoBasic as MovieInfoBasicDao
import com.example.movienight20.data.room.MovieScoutDatabase
import com.example.movienight20.data.room.RecentMovieId
import com.example.movienight20.domain.ActorRoleMovie
import com.example.movienight20.domain.Cast
import com.example.movienight20.domain.CrewRoleMovie
import com.example.movienight20.domain.Genre
import com.example.movienight20.domain.MovieCredits
import com.example.movienight20.domain.MovieDetails
import com.example.movienight20.domain.MoviesCollectionInfo
import com.example.movienight20.domain.MoviesRepository
import com.example.movienight20.domain.PeopleDetails
import com.example.movienight20.domain.PeopleMovieCredits
import com.example.movienight20.domain.MovieInfoBasic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val networkService: MovieDatabaseNetworkService,
    private val movieScoutDatabase: MovieScoutDatabase
) : MoviesRepository {

    //TODO: Move getMovies and getNowPlaying logic to helper function
    override suspend fun getMovies(): List<MoviesCollectionInfo> {
        val networkResponse = networkService.getPopularMovies()
        val results = networkResponse.body()?.results
        val mapped = results!!.map {
            Log.v(TAG, it.toString())
            MoviesCollectionInfo(
                id = it.id!!,
                title = it.title!!,
                backdropPath = it.backdropPath!!,
                posterPath = it.posterPath!!,
                releaseDate = it.releaseDate!!,
                rating = it.voteAverage!!.toString()
            )
        }
        return mapped
    }

    override suspend fun getNowPlaying(): List<MoviesCollectionInfo> {
        val networkResponse = networkService.getNowPlaying()
        val results = networkResponse.body()?.results
        val mapped = results!!.map {
            Log.v(TAG, it.toString())
            MoviesCollectionInfo(
                id = it.id!!,
                title = it.title!!,
                backdropPath = it.backdropPath!!,
                posterPath = it.posterPath!!,
                releaseDate = it.releaseDate!!,
                rating = it.voteAverage!!.toString()
            )
        }
        return mapped
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        val networkResponse = networkService.getMovieDetails(movieId = movieId)
        val results = networkResponse.body()

        return MovieDetails(
            id = results!!.id!!,
            title = results!!.title!!,
            backdropPath = results.backdropPath!!,
            overview = results.overview!!,
            runtime = results.runtime!!,
            status = results.status!!,
            genres = results.genres!!.map {
                Genre(id = it.id!!, title = it.title!!)
            },
            releaseDate = results.releaseDate!!,
            voteAvg = results.voteAvg!!,
            voteCount = results.voteCount!!,
            tagline = results.tagline!!,
            posterPath = results.posterPath!!
        )
    }

    private companion object {
        private const val TAG = "MovieRepositoryImpl"
    }

    override suspend fun getMovieCredits(movieId: Int): MovieCredits {
        val networkResponse = networkService.getMovieCredits(movieId = movieId)
        val results = networkResponse.body()
        return MovieCredits(
            movieId = results!!.movieId!!,
            cast = results.cast!!.map {
                Cast(
                    castId = it.castId!!,
                    name = it.name,
                    picturePath = it.picturePath,
                    character = it.character
                )
            }
        )
    }

    override suspend fun getPeopleMovieCredits(personId: Int): PeopleMovieCredits {
        val networkResponse = networkService.getPeopleMovieCredits(personId = personId)
        val results = networkResponse.body()
        return PeopleMovieCredits(
            actorRoleMovie = results!!.actorRoleMovie.map {
                ActorRoleMovie(
                    id = it.id,
                    posterPath = it.posterPath,
                    title = it.title,
                    releaseDate = it.releaseDate,
                    voteAvg = it.voteAvg
                )
            },
            crewRoleMovie = results.crewRoleMovie.map {
                CrewRoleMovie(
                    id = it.id,
                    posterPath = it.posterPath,
                    title = it.title,
                    releaseDate = it.releaseDate,
                    voteAvg = it.voteAvg
                )
            }
        )
    }

    override suspend fun getPeopleDetails(personId: Int): PeopleDetails {
        val networkResponse = networkService.getPeopleDetails(personId = personId)
        val results = networkResponse.body()
        return PeopleDetails(
            bio = results!!.bio.toString(),
            birthday = results.birthday.toString(),
            deathday = results.deathday.toString(),
            name = results.name.toString(),
            birthPlace = results.birthPlace.toString(),
            profilePath = results.profilePath.toString()
        )
    }

    /**** database functions ****/

    override suspend fun storeDataInCache(movie: MovieDetails) {
        val recentMovieId = RecentMovieId(id = movie.id)
        val movieInfoBasic =
            MovieInfoBasicDao(id = movie.id, posterPath = movie.posterPath, name = movie.title)

        movieScoutDatabase.recentMovieIds().insertMovieId(recentMovieId)
        movieScoutDatabase.movieInfoBasic().insertMovie(movieInfoBasic)
    }

//    override suspend fun getRecentlyViewed(): Flow<List<MovieInfoBasic>> = flow {
//        val data = movieScoutDatabase.movieInfoBasic().getRecentlyViewed()
//        val mapped = data.map {
//            MovieInfoBasic(
//                id = it.id,
//                posterPath = "http://image.tmdb.org/t/p/" + "w1280" + it.posterPath,
//                name = it.name
//            )
//        }.flowOn(Dispatchers.IO)
//    }

    override fun getRecentlyViewed(): Flow<List<MovieInfoBasic>> =
        movieScoutDatabase.movieInfoBasic().getRecentlyViewed()
            .map { dataList ->
                dataList.map { data ->
                    MovieInfoBasic(
                        id = data.id,
                        title = data.name,
                        posterPath = "http://image.tmdb.org/t/p/w1280${data.posterPath}"
                    )
                }
            }
            .flowOn(Dispatchers.IO)




}