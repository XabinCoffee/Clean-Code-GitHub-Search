package com.xabin.searchgithub.users.usecase

import com.xabin.searchgithub.common.database.SearchQueryDao
import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.search_queries.SearchQuery
import com.xabin.searchgithub.users.User
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val gitHubApi: GitHubApi, private val searchQueryDao: SearchQueryDao) {
    suspend operator fun invoke(query: String): List<User> {
        if (query != "") {
            try {
                val response = gitHubApi.searchUsers(query)
                if (response.isSuccessful) {
                    response.body()?.let {
                        searchQueryDao.upsert(SearchQuery(query, System.currentTimeMillis()))
                        return it.items.map { schema -> schema.toDomain() }
                    }
                }
            } catch (e: CancellationException) {
                // Works properly!
            } catch (e: Exception) {
                throw e
            }
        }
        return emptyList()
    }
}