package com.xabin.searchgithub.users.usecase

import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.users.Repository
import javax.inject.Inject

class FetchUserRepositoriesUseCase @Inject constructor(private val gitHubApi: GitHubApi) {
    suspend operator fun invoke(username: String): List<Repository> {
        try {
            val response = gitHubApi.getRepos(username)
            if (response.isSuccessful) {
                response.body()?.let {
                    val repos = mutableListOf<Repository>().apply {
                        it.forEach { repo ->
                            this.add(repo.toDomain())
                        }
                    }
                    return repos
                }
            }
        } catch (e: Exception) {
            throw e
        }

        return emptyList()
    }
}