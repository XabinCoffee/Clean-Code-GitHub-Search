package com.xabin.searchgithub.screens.profile

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xabin.searchgithub.R
import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.users.Repository
import com.xabin.searchgithub.users.User
import com.xabin.searchgithub.users.usecase.FetchUserFollowersUseCase
import com.xabin.searchgithub.users.usecase.FetchUserProfileUseCase
import com.xabin.searchgithub.users.usecase.FetchUserRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val application: Context,
    val gitHubApi: GitHubApi,
    val fetchUsersProfileUseCase: FetchUserProfileUseCase,
    val fetchUserFollowersUseCase: FetchUserFollowersUseCase,
    val fetchUserRepositoriesUseCase: FetchUserRepositoriesUseCase,
): ViewModel() {

    var user by mutableStateOf<User?>(null)
    var followers = mutableStateListOf<User>()
    var repos = mutableStateListOf<Repository>()

    var detailsLoading by mutableStateOf(true)
    var followersLoading by mutableStateOf(true)
    var reposLoading by mutableStateOf(true)

    var errorMessage by mutableStateOf<String?>(null)

    fun getUserProfile(username: String) = viewModelScope.launch(CoroutineName("getUserProfile")) {

        val scope = this

        launch(CoroutineName("Profile Details")) {
            println(this.coroutineContext)
            detailsLoading = true
            try {
                val userResp = fetchUsersProfileUseCase(username)
                user = userResp
            } catch (e: UnknownHostException) {
                if (errorMessage == null) errorMessage = application.getString(R.string.network_error)
                scope.coroutineContext.cancelChildren()
            } catch (e: Exception) {
                if (errorMessage == null) errorMessage = e.toString()
                scope.coroutineContext.cancelChildren()
                e.printStackTrace()
            }

            detailsLoading = false
        }

        launch(CoroutineName("Followers")) {
            println(this.coroutineContext)
            followersLoading =  true
            try {
                val followersResp = fetchUserFollowersUseCase(username)
                followers.clear()
                followers.addAll(followersResp)
            } catch (e: UnknownHostException) {
                if (errorMessage == null) errorMessage = application.getString(R.string.network_error)
                scope.coroutineContext.cancelChildren()

            } catch (e: Exception) {
                if (errorMessage == null) errorMessage = e.toString()
                e.printStackTrace()
                scope.coroutineContext.cancelChildren()

            }

            followersLoading = false
        }

        launch(CoroutineName("Repositories")) {
            println(this.coroutineContext)
            reposLoading = true
            try {
                val reposResp = fetchUserRepositoriesUseCase(username)
                repos.clear()
                repos.addAll(reposResp)
            } catch (e: UnknownHostException) {
                if (errorMessage == null) errorMessage = application.getString(R.string.network_error)
                scope.coroutineContext.cancelChildren()

            } catch (e: Exception) {
                if (errorMessage == null) errorMessage = e.toString()
                e.printStackTrace()
                scope.coroutineContext.cancelChildren()

            }

            reposLoading = false
        }

    }

    fun resetErrorMessage() {
        errorMessage = null
    }

}