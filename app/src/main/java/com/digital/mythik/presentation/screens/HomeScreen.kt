package com.digital.mythik.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.digital.mythik.R
import com.digital.mythik.domain.model.Video
import com.digital.mythik.presentation.components.VideoCarousel
import com.digital.mythik.presentation.components.VideoListItem
import com.digital.mythik.presentation.viewmodels.HomeViewModel

// Test tags for semantic testing
const val TAG_LOADING_INDICATOR = "loading_indicator"
const val TAG_ERROR_CONTAINER = "error_container"
const val TAG_RETRY_BUTTON = "retry_button"
const val TAG_VIDEO_LIST = "video_list"
const val TAG_TOP_BAR = "top_bar"

@Composable
fun HomeScreen(
    onVideoClick: (Video) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        Row(
            Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .shadow(elevation = 30.dp)
                .testTag(TAG_TOP_BAR)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag(TAG_LOADING_INDICATOR),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.Red)
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag(TAG_ERROR_CONTAINER),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = uiState.error!!,
                                color = Color.White,
                                modifier = Modifier.padding(16.dp)
                            )
                            Button(
                                onClick = { viewModel.retry() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                modifier = Modifier.testTag(TAG_RETRY_BUTTON)
                            ) {
                                Text(stringResource(R.string.retry), color = Color.White)
                            }
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.testTag(TAG_VIDEO_LIST)
                    ) {
                        // Hero Carousel
                        item {
                            Text(
                                text = stringResource(R.string.top_videos),
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        item {
                            if (uiState.carouselVideos.isNotEmpty()) {
                                VideoCarousel(
                                    videos = uiState.carouselVideos,
                                    onVideoClick = onVideoClick
                                )
                            }
                        }

                        // Videos List Header
                        item {
                            Text(
                                text = stringResource(R.string.all_videos),
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        items(items = uiState.videos) { video ->
                            VideoListItem(
                                video = video,
                                onVideoClick = { onVideoClick(video) }
                            )
                        }
                    }
                }
            }
        }
    }
}