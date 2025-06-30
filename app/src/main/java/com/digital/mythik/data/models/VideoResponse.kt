package com.digital.mythik.data.models

import com.digital.mythik.domain.model.Video


data class VideoResponse(
    val id: String,
    val title: String,
    val thumbnailUrl: String,
    val duration: String,
    val uploadTime: String,
    val views: String,
    val author: String,
    val videoUrl: String,
    val description: String,
    val subscriber: String,
    val isLive: Boolean
) {
    fun toDomain(): Video {
        return Video(
            id = id,
            title = title,
            thumbnailUrl = thumbnailUrl,
            duration = duration,
            uploadTime = uploadTime,
            views = views,
            author = author,
            videoUrl = videoUrl,
            description = description,
            subscriber = subscriber,
            isLive = isLive
        )
    }
}