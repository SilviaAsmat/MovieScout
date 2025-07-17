package com.example.movienight20.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movienight20.data.network_response.MoviesCollectionsNetworkResponse
import com.example.movienight20.data.room.MovieInfoBasicEntity
import com.example.movienight20.data.room.MovieScoutDatabase
import com.example.movienight20.data.room.RecentMovieIdEntity
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
import retrofit2.Response
import javax.inject.Inject
import kotlin.collections.map

class MovieRepositoryImpl @Inject constructor(
    private val networkService: MovieDatabaseNetworkService,
    private val movieScoutDatabase: MovieScoutDatabase
) : MoviesRepository {

    override suspend fun getMovies(): List<MoviesCollectionInfo> {
        val networkResponse = networkService.getPopularMovies(page = 1)
        return mapMovieCollectionNetworkResponse(networkResponse)
    }

    override suspend fun getNowPlaying(): List<MoviesCollectionInfo> {
        val networkResponse = networkService.getNowPlaying()
        return mapMovieCollectionNetworkResponse(networkResponse)
    }

    override suspend fun getUpcomingMovies(): List<MoviesCollectionInfo> {
        val networkResponse = networkService.getUpcomingMovies()
        return mapMovieCollectionNetworkResponse(networkResponse)
    }

    override suspend fun getTopRated(): List<MoviesCollectionInfo> {
        val networkResponse = networkService.getTopRatedMovies()
        return mapMovieCollectionNetworkResponse(networkResponse)
    }

    private fun mapMovieCollectionNetworkResponse(networkResponse: Response<MoviesCollectionsNetworkResponse>): List<MoviesCollectionInfo> {
        val results = networkResponse.body()?.results
        val mapped = results!!.map {
            Log.v(TAG, it.toString())
            MoviesCollectionInfo(
                id = it.id!!,
                title = it.title!!,
                backdropPath = it.backdropPath.toString(),
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
            title = results.title!!,
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
            posterPath = results.posterPath!!,
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
        val recentMovieIdEntity = RecentMovieIdEntity(id = movie.id)
        val movieInfoBasic =
            MovieInfoBasicEntity(
                id = movie.id,
                posterPath = movie.posterPath,
                name = movie.title,
                backdropPath = movie.backdropPath,
            )

        movieScoutDatabase.recentMovieIdsDao().insertMovieId(recentMovieIdEntity)
        movieScoutDatabase.movieInfoBasicDao().insertMovie(movieInfoBasic)
    }

    override fun getRecentlyViewed(): Flow<List<MovieInfoBasic>> =
        movieScoutDatabase.movieInfoBasicDao().getRecentlyViewed()
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

    @OptIn(ExperimentalPagingApi::class)
    override fun popularMoviesPagination(): Flow<PagingData<MovieInfoBasicEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false),
            remoteMediator = MoviesRemoteMediator(networkService, movieScoutDatabase),
            pagingSourceFactory = {
                movieScoutDatabase.movieInfoBasicDao().getMoviesPagingSource()
            }
        ).flow
    }

//    override fun popularMoviesSinglePage(): Flow<List<MovieInfoBasic>> = flow {
//        val response = networkService.getPopularMovies(1)
//        val movies = response.body()?.results?.map {
//            movieMapper.movieResponseToMovieSummary(it)
//        } ?: emptyList()
//
//        emit(movies)
//    }

//    override fun popularMoviesSinglePage(): Flow<List<MoviesCollectionInfo>> {
//        return List<MoviesCollectionInfo>(size = null){
//            override suspend fun fetchData(): List<MoviesCollectionInfo>{
//                val networkResponse = networkService.getPopularMovies(page = 1)
//                return mapMovieCollectionNetworkResponse(networkResponse)
//            }
//        }.load()
//    }
//    override fun popularMoviesSinglePage(): Flow<List<MovieInfoBasic>> {
//        return List<MoviesCollectionInfo>{
//            override suspend fun getPopularMovies(): MoviesCollectionsNetworkResponse<List<MoviesCollectionInfo>> {
//                val response = tmdbClient.getPopularMoviesSuspend(1)
//                return response.body()?.results?.map {
//                    movieMapper.movieResponseToMovieSummary(it)
//                }?.let { data ->
//                    NetworkResponse.Success(data)
//                } ?: run {
//                    NetworkResponse.Failure(response.message())
//                }
//            }
//        }.load()
//    }


}