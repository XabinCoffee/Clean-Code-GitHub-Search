package com.xabin.searchgithub.users.usecase

import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.users.User
import javax.inject.Inject

class FetchUserProfileUseCase @Inject constructor(private val gitHubApi: GitHubApi) {
    suspend operator fun invoke(username: String): User? {
        try {
            val response = gitHubApi.getUserProfile(username)
            if (response.isSuccessful) {
                response.body()?.let {
                    return it.toDomain()
                }
            }
        } catch (e: Exception) {
            throw e
        }
        return null
    }
}