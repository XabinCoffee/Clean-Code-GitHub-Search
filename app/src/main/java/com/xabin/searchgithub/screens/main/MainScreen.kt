package com.xabin.searchgithub.screens.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xabin.searchgithub.screens.Route
import com.xabin.searchgithub.screens.ScreensNavigator
import com.xabin.searchgithub.screens.search.SearchScreen


@Composable
fun MainScreen() {

    val screensNavigator = remember() {
        ScreensNavigator()
    }

    val currentRoute = screensNavigator.currentRoute.collectAsState()

    Scaffold(
        /*topBar = {
            MyTopAppBar(
                isRootRoute = isRootRoute.value,
                isShowFavoriteButton = isShowFavoriteButton.value,
                isFavoriteQuestion = isFavoriteQuestion,
                questionIdAndTitle = questionIdAndTitle,
                onToggleFavoriteClicked = {
                    mainViewModel.toggleFavoritedQuestion(questionIdAndTitle.first, questionIdAndTitle.second)
                },
                onBackClicked = {
                    screensNavigator.navigateBack()
                }
            )
        },*/
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

    Surface(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 12.dp),
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
                    showStartup = false,
                    onSearchInteraction = {  },
                    screensNavigator = screensNavigator,
                    isPortrait = false
                )
            }

            composable(route = Route.ProfileScreen().routeName) {
                val username = remember {
                    (screensNavigator.currentRoute.value as Route.ProfileScreen).username
                }

            }
        }
    }
}
