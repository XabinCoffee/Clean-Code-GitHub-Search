package com.xabin.searchgithub.screens.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xabin.searchgithub.screens.Route
import com.xabin.searchgithub.screens.ScreensNavigator
import com.xabin.searchgithub.screens.profile.ProfileScreen
import com.xabin.searchgithub.screens.search.SearchScreen


@Composable
fun MainScreen() {

    val screensNavigator = remember() {
        ScreensNavigator()
    }

    val currentRoute = screensNavigator.currentRoute.collectAsState()

    Scaffold(
        content = { padding ->
            MainScreenContent(
                padding = padding,
                screensNavigator = screensNavigator
            )
        }
    )

}


@Composable
private fun MainScreenContent(
    padding: PaddingValues,
    screensNavigator: ScreensNavigator
) {
    val parentNavController = rememberNavController()
    screensNavigator.setNavController(parentNavController)

    var showStartup by rememberSaveable { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .padding(padding),
    ) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = parentNavController,
            enterTransition = { fadeIn(animationSpec = tween(200)) },
            exitTransition = { fadeOut(animationSpec = tween(200)) },
            startDestination = Route.SearchScreen.routeName,
        ) {
            composable(route = Route.SearchScreen.routeName) {
                SearchScreen(
                    showStartup = showStartup,
                    onSearchInteraction = { showStartup = false },
                    screensNavigator = screensNavigator
                )
            }

            composable(route = Route.ProfileScreen().routeName) {
                val username = remember {
                    (screensNavigator.currentRoute.value as Route.ProfileScreen).username
                }

                ProfileScreen(
                    username = username,
                    screensNavigator = screensNavigator
                )

            }
        }
    }
}
