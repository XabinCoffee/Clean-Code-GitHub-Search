package com.xabin.searchgithub.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.users.SearchUsersUseCase
import com.xabin.searchgithub.users.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val gitHubApi: GitHubApi, val searchUsersUseCase: SearchUsersUseCase): ViewModel() {

    private val USERS_PER_PAGE = 20
    private var job: Job? = null

    // SEARCH
    var searchUsers = mutableStateListOf<User>()
    var currentPage by mutableIntStateOf(1)

    var loading by mutableStateOf(false)
    var searchText by mutableStateOf("")

    init {
        searchUsers = emptyList<User>().toMutableStateList()

    }

    // Whenever the user edits the search bar we start a one second timer
    // this function will only call GitHub's API whenever the user stops typing for 1 second to avoid unnecesary API calls

    fun searchAfterUserStopsTyping() {
        viewModelScope.launch {
            job?.cancel()
            job = launch {
                if (searchText == "") {
                    searchUsers.clear()
                    loading = false
                } else {
                    loading = true
                    delay(1000)
                    searchUsers.clear()
                    // load
                    loading = false
                }
            }
        }
    }


}