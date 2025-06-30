package com.digital.mythik.domain.usecase

import com.digital.mythik.domain.model.Video
import com.digital.mythik.domain.repository.ContentRepository

class GetVideosUseCase(
    private val repository: ContentRepository
) {
    suspend operator fun invoke(): Result<List<Video>> {
        return repository.getVideos()
    }
}
