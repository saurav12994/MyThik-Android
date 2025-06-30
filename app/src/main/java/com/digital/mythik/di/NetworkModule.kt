package com.digital.mythik.di

import com.digital.mythik.data.api.VideoApiService
import com.digital.mythik.data.repositoryImpl.ContentRepositoryImpl
import com.digital.mythik.domain.repository.ContentRepository
import com.digital.mythik.domain.usecase.GetVideosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/poudyalanil/ca84582cbeb4fc123a13290a586da925/raw/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideContentApiService(retrofit: Retrofit): VideoApiService {
        return retrofit.create(VideoApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideContentRepository(apiService: VideoApiService): ContentRepository {
        return ContentRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetContentUseCase(repository: ContentRepository): GetVideosUseCase {
        return GetVideosUseCase(repository)
    }
}