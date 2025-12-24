package com.xabin.searchgithub.screens


import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow

class ScreensNavigator {

    private lateinit var navController: NavHostController

    private var navControllerObserveJob: Job? = null

    val currentRoute = MutableStateFlow<Route?>(null)

    fun setNavController(navController: NavHostController) {
        this.navController = navController
        navControllerObserveJob?.cancel()
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun toRoute(route: Route) {
        navController.navigate(route.navCommand)
        currentRoute.value = route
    }

}