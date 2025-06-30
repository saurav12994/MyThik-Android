package com.digital.mythik.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.digital.mythik.domain.model.Video
import com.digital.mythik.presentation.screens.HomeScreen
import com.digital.mythik.presentation.screens.VideoPlayerScreen

@Composable
fun VideoAppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onVideoClick = { video ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("video", video)
                    navController.navigate("player")
                }
            )
        }

        composable("player") { backStackEntry ->
            val video = navController.previousBackStackEntry?.savedStateHandle?.get<Video>("video")
            video?.let {
                VideoPlayerScreen(
                    video = it,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
