package com.xabin.searchgithub.users.usecase

import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.users.User
import javax.inject.Inject

class FetchUserFollowersUseCase @Inject constructor(private val gitHubApi: GitHubApi) {
    suspend operator fun invoke(username: String): List<User> {
        try {
            val response = gitHubApi.getFollowers(username)
            if (response.isSuccessful) {
                response.body()?.let {
                    val users = mutableListOf<User>().apply {
                        it.forEach { user ->
                            this.add(user.toDomain())
                        }
                    }
                    return users
                }
            }
        } catch (e: Exception) {
            throw e
        }

        return emptyList()
    }
}