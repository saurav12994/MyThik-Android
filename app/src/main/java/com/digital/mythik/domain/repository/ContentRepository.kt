package com.digital.mythik.domain.repository

import com.digital.mythik.domain.model.Video

interface ContentRepository {
    suspend fun getVideos(): Result<List<Video>>
}