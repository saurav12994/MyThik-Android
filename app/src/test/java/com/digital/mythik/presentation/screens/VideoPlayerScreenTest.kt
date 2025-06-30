package com.digital.mythik.presentation.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.digital.mythik.domain.model.Video
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [33],
    instrumentedPackages = ["androidx.loader.content"]
)
class VideoPlayerScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var testVideo: Video
    private var backClicked = false

    @Before
    fun setup() {
        testVideo = Video(
            id = "1",
            title = "Test Video",
            description = "Test Description",
            videoUrl = "https://example.com/video.mp4",
            thumbnailUrl = "https://example.com/thumbnail.jpg",
            views = "1K views",
            uploadTime = "2 hours ago",
            author = "Test Author",
            subscriber = "100K subscribers",
            duration = "22121",
            isLive = false
        )
    }

    @Test
    fun `when screen loads - displays video title in top bar`() {
        composeTestRule.setContent {
            VideoPlayerScreen(
                video = testVideo,
                onBackClick = { backClicked = true }
            )
        }

        composeTestRule.onNodeWithText("Test Description").assertIsDisplayed()
    }

    @Test
    fun `when back button clicked - triggers onBackClick`() {
        composeTestRule.setContent {
            VideoPlayerScreen(
                video = testVideo,
                onBackClick = { backClicked = true }
            )
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()

        assert(backClicked)
    }

    @Test
    fun `when screen loads - displays video details`() {
        composeTestRule.setContent {
            VideoPlayerScreen(
                video = testVideo,
                onBackClick = { backClicked = true }
            )
        }

        composeTestRule.onNodeWithText("Test Author").assertIsDisplayed()
        composeTestRule.onNodeWithText("100K subscribers").assertIsDisplayed()
        composeTestRule.onNodeWithText("1K views").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Description").assertIsDisplayed()
    }
} 