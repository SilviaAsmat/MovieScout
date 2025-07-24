package com.example.movienight20.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movienight20.data.network_response.MoviesCollectionsNetworkResponse
import com.example.movienight20.data.room.movie_info_basic.MovieInfoBasicEntity
import com.example.movienight20.data.room.MovieScoutDatabase
import com.example.movienight20.data.room.recently_viewed.RecentMovieIdEntity
import com.example.movienight20.domain.ActorRoleMovie
import com.example.movienight20.domain.Cast
import com.example.movienight20.domain.CrewRoleMovie
import com.example.movienight20.domain.Genre
import com.example.movienight20.domain.MovieCredits
import com.example.movienight20.domain.MovieDetails
import com.example.movienight20.domain.MovieCollectionItem
import com.example.movienight20.domain.MoviesRepository
import com.example.movienight20.domain.PeopleDetails
import com.example.movienight20.domain.PeopleMovieCredits
import com.example.movienight20.domain.MovieInfoBasic
import com.example.movienight20.domain.MovieVideo
import com.example.movienight20.ui.movie_collection_type.MovieCollectionType
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

    override suspend fun getPopularMovies(): List<MovieCollectionItem> {
        val networkResponse = networkService.getPopularMovies(page = FIRST_PAGE)
        return mapMovieCollectionNetworkResponse(networkResponse)
    }

    override suspend fun getNowPlaying(): List<MovieCollectionItem> {
        val networkResponse = networkService.getNowPlaying(page = FIRST_PAGE)
        return mapMovieCollectionNetworkResponse(networkResponse)
    }

    override suspend fun getUpcomingMovies(): List<MovieCollectionItem> {
        val networkResponse = networkService.getUpcomingMovies(page = FIRST_PAGE)
        return mapMovieCollectionNetworkResponse(networkResponse)
    }

    override suspend fun getTopRated(): List<MovieCollectionItem> {
        val networkResponse = networkService.getTopRatedMovies(page = FIRST_PAGE)
        return mapMovieCollectionNetworkResponse(networkResponse)
    }

    private fun mapMovieCollectionNetworkResponse(
        networkResponse: Response<MoviesCollectionsNetworkResponse>,
    ): List<MovieCollectionItem> {
        val results = networkResponse.body()?.results
        val mapped = results!!.map {
            MovieCollectionItem(
                id = it.id!!,
                title = it.title ?: "",
                backdropPath = it.backdropPath ?: "",
                posterPath = it.posterPath ?: "",
                releaseDate = it.releaseDate ?: "",
                rating = (it.voteAverage ?: "").toString()
            )
        }
        return mapped
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        val networkResponse = networkService.getMovieDetails(movieId = movieId)
        val results = networkResponse.body()

        return MovieDetails(
            id = results!!.id ?: 0,
            title = results.title ?: "",
            backdropPath = results.backdropPath ?: "",
            overview = results.overview ?: "",
            runtime = results.runtime ?: 0,
            status = results.status ?: "",
            genres = results.genres!!.map {
                Genre(id = it.id ?: 0, title = it.title ?: "")
            },
            releaseDate = results.releaseDate ?: "",
            voteAvg = results.voteAvg ?: 0,
            voteCount = results.voteCount ?: 0,
            tagline = results.tagline ?: "",
            posterPath = results.posterPath ?: "",
        )
    }

    override suspend fun getMovieCredits(movieId: Int): MovieCredits {
        val networkResponse = networkService.getMovieCredits(movieId = movieId)
        val results = networkResponse.body()
        return MovieCredits(
            movieId = results!!.movieId ?: 0,
            cast = results.cast!!.map {
                Cast(
                    castId = it.castId ?: 0,
                    name = it.name ?: "",
                    picturePath = it.picturePath ?: "",
                    character = it.character ?: ""
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
                    id = it.id ?: 0,
                    posterPath = it.posterPath ?: "",
                    title = it.title ?: "",
                    releaseDate = it.releaseDate ?: "",
                    voteAvg = it.voteAvg ?: ""
                )
            },
            crewRoleMovie = results.crewRoleMovie.map {
                CrewRoleMovie(
                    id = it.id ?: 0,
                    posterPath = it.posterPath ?: "",
                    title = it.title ?: "",
                    releaseDate = it.releaseDate ?: "",
                    voteAvg = it.voteAvg ?: ""
                )
            }
        )
    }

    override suspend fun getPeopleDetails(personId: Int): PeopleDetails {
        val networkResponse = networkService.getPeopleDetails(personId = personId)
        val results = networkResponse.body()
        return PeopleDetails(
            bio = results!!.bio ?: "",
            birthday = results.birthday ?: "",
            deathday = results.deathday ?: "",
            name = results.name ?: "",
            birthPlace = results.birthPlace ?: "",
            profilePath = results.profilePath ?: ""
        )
    }

    override suspend fun getMovieVideos(movieId: Int): List<MovieVideo> {
        val networkResponse = networkService.getMovieVideos(movieId = movieId)
        val results = networkResponse.body()?.result
        return results!!.map{
            MovieVideo(
                videoPath = "https://www.youtube.com/watch?v=" + it.key.toString(),
                thumbnailPath = "http://img.youtube.com/vi/" + it.key.toString() + "/0.jpg",
                title = it.title?:"",
                official = it.official?:false,
                type = it.type?:"",
                size = it.size?:0
            )
        }
    }

    /**** database functions ****/

    override suspend fun storeRecentlyViewed(movie: MovieDetails) {
        val recentMovieIdEntity = RecentMovieIdEntity(id = movie.id)
        val movieInfoBasic =
            MovieInfoBasicEntity(
                remoteId = movie.id,
                posterPath = movie.posterPath,
                name = movie.title,
                backdropPath = movie.backdropPath
            )

        movieScoutDatabase.recentMovieIdsDao().insertMovieId(recentMovieIdEntity)
        movieScoutDatabase.movieInfoBasicDao().insertMovie(movieInfoBasic)
    }

    override fun getRecentlyViewed(): Flow<List<MovieInfoBasic>> =
        movieScoutDatabase.movieInfoBasicDao().getRecentlyViewed()
            .map { dataList ->
                dataList.map { data ->
                    MovieInfoBasic(
                        id = data.remoteId,
                        title = data.name,
                        posterPath = "http://image.tmdb.org/t/p/w1280${data.posterPath}"
                    )
                }
            }
            .flowOn(Dispatchers.IO)

    /****  Pagination  ****/
    @OptIn(ExperimentalPagingApi::class)
    override fun moviesPagination(movieCollectionType: MovieCollectionType): Flow<PagingData<MovieInfoBasicEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = MoviesRemoteMediator(
                networkService,
                movieScoutDatabase,
                collectionType = movieCollectionType
            ),
            pagingSourceFactory = {
                when (movieCollectionType) {
                    MovieCollectionType.POPULAR -> movieScoutDatabase.getPopularMoviesDao()
                        .getPopularMovies()

                    MovieCollectionType.NOW_PLAYING -> movieScoutDatabase.getNowPlayingMoviesDao()
                        .getNowPlayingMovies()

                    MovieCollectionType.TOP_RATED -> movieScoutDatabase.getTopRatedMoviesDao()
                        .getTopRatedMovies()

                    MovieCollectionType.UPCOMING -> movieScoutDatabase.getUpcomingMoviesDao()
                        .getUpcomingMovies()
                }
            }
        ).flow
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}