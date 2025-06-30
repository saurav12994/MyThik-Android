package com.digital.mythik.data.repositoryImpl

import com.digital.mythik.data.api.VideoApiService
import com.digital.mythik.domain.model.Video
import com.digital.mythik.domain.repository.ContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ContentRepositoryImpl @Inject constructor(
    private val apiService: VideoApiService
) : ContentRepository {

    override suspend fun getVideos(): Result<List<Video>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getVideos()
                val videos = response.map { it.toDomain() }
                Result.success(videos)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
