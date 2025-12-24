package com.xabin.searchgithub.common.di

import android.app.Application
import androidx.room.Room
import com.xabin.searchgithub.BuildConfig
import com.xabin.searchgithub.common.database.AppRoomDatabase
import com.xabin.searchgithub.common.database.SearchQueryDao
import com.xabin.searchgithub.networking.GitHubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun retrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder().run {
            addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
            build()
        }

        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun appRoomDatabase(application: Application): AppRoomDatabase {
        return Room.databaseBuilder(
            application,
            AppRoomDatabase::class.java,
            "app_db"
        ).build()
    }
    @Provides
    fun searchQueryDao(database: AppRoomDatabase): SearchQueryDao {
        return database.searchQueryDao
    }

    @Provides
    fun gitHubApi(retrofit: Retrofit): GitHubApi {
        return retrofit.create(GitHubApi::class.java)
    }


}