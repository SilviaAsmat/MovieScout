package com.example.movienight20.di

import android.util.Log
import com.example.movienight20.BuildConfig
import com.example.movienight20.data.MovieDatabaseNetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

class NetworkServiceModule {
    @Singleton
    @Provides
    fun provideMovieDatabaseNetworkService(): MovieDatabaseNetworkService {
        val httpClient = OkHttpClient().newBuilder().addInterceptor(MyInterceptor()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        val service: MovieDatabaseNetworkService = retrofit.create(MovieDatabaseNetworkService::class.java)
        return service
    }
}

private class MyInterceptor : Interceptor{
    companion object {
        const val API_KEY = "api_key"
        const val LANGUAGE = "language"
        const val REGION = "region"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val httpURL = originalRequest.url
Log.v("SAA", httpURL.toString())
        val newHttpURLBuilder = httpURL.newBuilder()

        newHttpURLBuilder.addQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
        newHttpURLBuilder.addQueryParameter(LANGUAGE, Locale.getDefault().language)
        newHttpURLBuilder.addQueryParameter(REGION, Locale.getDefault().country)
        val newHttpURL = newHttpURLBuilder.build()
        val newRequest = originalRequest.newBuilder().url(newHttpURL).build()
        return chain.proceed(newRequest)
    }

}