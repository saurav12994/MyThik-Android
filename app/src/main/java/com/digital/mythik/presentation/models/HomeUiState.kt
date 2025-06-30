package com.digital.mythik.presentation.models

import com.digital.mythik.domain.model.Video


data class HomeUiState(
    val isLoading: Boolean = false,
    val videos: List<Video> = emptyList(),
    val carouselVideos: List<Video> = emptyList(),
    val error: String? = null
)