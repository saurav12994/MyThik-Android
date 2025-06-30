package com.digital.mythik.data.api


import com.digital.mythik.data.models.VideoResponse
import retrofit2.http.GET

interface VideoApiService {

    @GET("videos.json")
    suspend fun getVideos(): List<VideoResponse>
}
