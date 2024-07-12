package com.xabin.searchgithub.screens.search

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xabin.searchgithub.screens.Route
import com.xabin.searchgithub.screens.ScreensNavigator
import com.xabin.searchgithub.screens.search.components.BottomSection
import com.xabin.searchgithub.screens.search.components.SearchBar


@Composable
fun SearchScreen(
    showStartup: Boolean,
    onSearchInteraction: () -> Unit,
    screensNavigator: ScreensNavigator,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    searchViewModel.errorMessage?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_LONG).show()
        searchViewModel.resetErrorMessage()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(16.dp))
        SearchBar(
            text = searchViewModel.searchText,
            onSearchModified = { text ->
                searchViewModel.searchText = text
                searchViewModel.searchAfterUserStopsTyping()
                onSearchInteraction()
            },
            onSearchClicked = {
                keyboardController?.hide()
                onSearchInteraction()
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(16.dp))

        BottomSection(
            showStartup = showStartup,
            loading = searchViewModel.loading,
            users = searchViewModel.searchUsers,
            page = searchViewModel.currentPage,
            onPageIncrease = { searchViewModel.currentPage++ },
            onPageDecrease = { searchViewModel.currentPage-- },
            onUserSelected = { login: String ->
                screensNavigator.toRoute(Route.ProfileScreen(login))
            }
        )

        Spacer(Modifier.height(16.dp))
    }
}

