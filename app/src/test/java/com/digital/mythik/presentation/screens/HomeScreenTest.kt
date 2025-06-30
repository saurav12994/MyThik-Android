package com.digital.mythik.presentation.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.digital.mythik.domain.model.Video
import com.digital.mythik.presentation.models.HomeUiState
import com.digital.mythik.presentation.viewmodels.HomeViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel = mockk<HomeViewModel>(relaxed = true)

    @Test
    fun `when loading state, shows loading indicator`() {
        val uiState = HomeUiState(isLoading = true)
        every { mockViewModel.uiState } returns MutableStateFlow(uiState)

        composeTestRule.setContent {
            HomeScreen(
                onVideoClick = {},
                viewModel = mockViewModel
            )
        }

        composeTestRule.onNodeWithTag(TAG_LOADING_INDICATOR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_TOP_BAR).assertIsDisplayed()
    }

    @Test
    fun `when error state, shows error message and retry button`() {
        val errorMessage = "Network error occurred"
        val uiState = HomeUiState(error = errorMessage)
        every { mockViewModel.uiState } returns MutableStateFlow(uiState)

        composeTestRule.setContent {
            HomeScreen(
                onVideoClick = {},
                viewModel = mockViewModel
            )
        }

        composeTestRule.onNodeWithTag(TAG_ERROR_CONTAINER).assertIsDisplayed()
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_RETRY_BUTTON).assertIsDisplayed()
    }

    @Test
    fun `when retry button clicked, calls retry on viewModel`() {
        val uiState = HomeUiState(error = "Error")
        every { mockViewModel.uiState } returns MutableStateFlow(uiState)

        composeTestRule.setContent {
            HomeScreen(
                onVideoClick = {},
                viewModel = mockViewModel
            )
        }
        composeTestRule.onNodeWithTag(TAG_RETRY_BUTTON).performClick()

        verify { mockViewModel.retry() }
    }

    @Test
    fun `when success state, shows videos in carousel and list`() {
        val videos = listOf(
            Video(
                id = "1",
                title = "Test Video",
                description = "Test Description",
                thumbnailUrl = "https://example.com/thumbnail.jpg",
                videoUrl = "https://example.com/video.mp4",
                duration = "326336",
                uploadTime = "2172171",
                views = "23232",
                author = "Sourabh",
                subscriber = "212",
                isLive = false
            )
        )
        val uiState = HomeUiState(
            isLoading = false,
            error = null,
            videos = videos,
            carouselVideos = videos
        )
        every { mockViewModel.uiState } returns MutableStateFlow(uiState)

        composeTestRule.setContent {
            HomeScreen(
                onVideoClick = {},
                viewModel = mockViewModel
            )
        }

        composeTestRule.onNodeWithTag(TAG_VIDEO_LIST).assertIsDisplayed()
        composeTestRule.onNodeWithText("Top Videos").assertIsDisplayed()
        composeTestRule.onNodeWithText("All Videos").assertIsDisplayed()
    }

    @Test
    fun `when video clicked, calls onVideoClick callback`() {
        val video = Video(
            id = "1",
            title = "Test Video",
            description = "Test Description",
            thumbnailUrl = "https://example.com/thumbnail.jpg",
            videoUrl = "https://example.com/video.mp4",
            duration = "326336",
            uploadTime = "2172171",
            views = "23232",
            author = "Sourabh",
            subscriber = "212",
            isLive = false
        )
        val secondVideo = Video(
            id = "1",
            title = "Test Video 2",
            description = "Test Description",
            thumbnailUrl = "https://example.com/thumbnail.jpg",
            videoUrl = "https://example.com/video.mp4",
            duration = "326336",
            uploadTime = "2172171",
            views = "23232",
            author = "Sourabh",
            subscriber = "212",
            isLive = false
        )
        val videos = listOf(video)
        val uiState = HomeUiState(
            isLoading = false,
            error = null,
            videos = videos,
            carouselVideos = listOf(secondVideo)
        )
        every { mockViewModel.uiState } returns MutableStateFlow(uiState)
        var clickedVideo: Video? = null

        composeTestRule.setContent {
            HomeScreen(
                onVideoClick = { clickedVideo = it },
                viewModel = mockViewModel
            )
        }
        composeTestRule.onNodeWithText("Test Video").performClick()

        assert(clickedVideo == video)
    }
} 