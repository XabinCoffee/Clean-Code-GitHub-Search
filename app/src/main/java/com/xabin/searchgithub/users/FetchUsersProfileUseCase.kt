package com.xabin.searchgithub.users

import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.networking.repositories.RepositorySchema
import com.xabin.searchgithub.networking.users.UserSchema
import com.xabin.searchgithub.search_queries.SearchQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchUsersProfileUseCase @Inject constructor(private val gitHubApi: GitHubApi) {
    suspend fun fetchUserProfile(username: String) = withContext(Dispatchers.IO) {
        try {
            val response = gitHubApi.getUserProfile(username)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@withContext userSchemaToUser(it)
                }
            }
        } catch (e: Exception) {
            throw e
        }

        null
    }

    suspend fun fetchUserFollowers(username: String) = withContext(Dispatchers.IO) {
        try {
            val response = gitHubApi.getFollowers(username)
            if (response.isSuccessful) {
                response.body()?.let {
                    val users = mutableListOf<User>().apply {
                        it.forEach { user ->
                            this.add(userSchemaToUser(user))
                        }
                    }
                    return@withContext users
                }
            }
        } catch (e: Exception) {
            throw e
        }

        emptyList()
    }

    suspend fun fetchUserRepos(username: String) = withContext(Dispatchers.IO) {
        try {
            val response = gitHubApi.getRepos(username)
            if (response.isSuccessful) {
                response.body()?.let {
                    val users = mutableListOf<Repository>().apply {
                        it.forEach { repo ->
                            this.add(repositorySchemaToRepository(repo))
                        }
                    }
                    return@withContext users
                }
            }
        } catch (e: Exception) {
            throw e
        }

        emptyList()
    }



    private fun userSchemaToUser(user: UserSchema): User {
        return User(
            id = user.id,
            username = user.login,
            name = user.name,
            url = user.url,
            avatarUrl = user.avatarUrl,
            bio = user.bio ?: "",
            followers = user.followers,
            following = user.following,
            publicRepos = user.publicRepos
        )
    }

    private fun repositorySchemaToRepository(repo: RepositorySchema): Repository {
        return Repository(
            name = repo.name,
            htmlUrl = repo.htmlUrl,
            description = repo.description,
            language = repo.language
        )
    }
}