package com.xabin.searchgithub.screens

sealed class Route(val routeName: String) {
    data object SearchScreen: Route("searchScreen")
    data class ProfileScreen(val username: String = ""): Route("profileScreen/{username}") {
        override val navCommand: String
            get() = routeName
                .replace("{username}", username)
    }

    open val navCommand = routeName
}