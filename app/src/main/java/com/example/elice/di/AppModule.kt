package com.example.elice.di

import android.content.Context
import androidx.room.Room
import com.example.elice.api.RemoteRepository
import com.example.elice.api.ApiHelperImpl
import com.example.elice.api.ApiService
import com.example.elice.data.local.repository.CourseDao
import com.example.elice.data.local.repository.CourseDatabase
import com.example.elice.other.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): RemoteRepository = apiHelper

    @Provides
    fun provideCourseDao(appDatabase: CourseDatabase): CourseDao {
        return appDatabase.courseDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CourseDatabase {
        return Room.databaseBuilder(
            appContext,
            CourseDatabase::class.java,
            "CourseDB"
        ).build()
    }


}