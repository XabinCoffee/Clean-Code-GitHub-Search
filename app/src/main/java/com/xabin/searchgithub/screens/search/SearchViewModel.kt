package com.xabin.searchgithub.screens.search

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xabin.searchgithub.R
import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.users.usecase.SearchUsersUseCase
import com.xabin.searchgithub.users.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @ApplicationContext private val application: Context,
     val gitHubApi: GitHubApi,
     val searchUsersUseCase: SearchUsersUseCase
) : ViewModel() {

    private val USERS_PER_PAGE = 20

    // SEARCH
    var searchUsers = mutableStateListOf<User>()
    var currentPage by mutableIntStateOf(1)

    var loading by mutableStateOf(false)
    var searchText by mutableStateOf("")

    var errorMessage by mutableStateOf<String?>(null)

    init {
        searchUsers = emptyList<User>().toMutableStateList()
    }

    // Whenever the user edits the search bar we start a one second timer
    // this function will only call GitHub's API whenever the user stops typing for 1 second to avoid unnecesary API calls
    fun searchAfterUserStopsTyping() {
        viewModelScope.coroutineContext.cancelChildren()
        viewModelScope.launch {
            loading = true
            delay(1000)
            searchUsers.clear()
            try {
                val users = searchUsersUseCase(searchText)
                searchUsers.addAll(users)
            } catch (e: UnknownHostException) {
                errorMessage = application.getString(R.string.network_error)
            } catch (e: Exception) {
                errorMessage = e.toString()
                e.printStackTrace()
            }
            loading = false
        }
    }

    fun resetErrorMessage() {
        errorMessage = null
    }
}