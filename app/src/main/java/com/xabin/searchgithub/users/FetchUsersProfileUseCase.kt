package com.xabin.searchgithub.users

import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.networking.users.UserSchema
import com.xabin.searchgithub.search_queries.SearchQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchUsersProfileUseCase @Inject constructor(private val gitHubApi: GitHubApi) {
    suspend fun fetchUserProfile(username: String) = withContext(Dispatchers.IO) {
        val response = gitHubApi.getUserProfile(username)
        if (response.isSuccessful) {
            response.body()?.let {
                return@withContext userSchemaToProfile(it)
            }
        }
        null
    }

    fun userSchemaToProfile(user: UserSchema): UserProfile {
        return UserProfile(
            id = user.id,
            username = user.login,
            name = user.name,
            url = user.url,
            followers = listOf()
        )
    }
}