package com.xabin.searchgithub.users

import com.xabin.searchgithub.common.database.SearchQueryDao
import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.networking.users.UserSchema
import com.xabin.searchgithub.search_queries.SearchQuery
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val gitHubApi: GitHubApi, private val searchQueryDao: SearchQueryDao) {

    private var users: List<User> = emptyList()

    suspend fun searchUsers(query: String) = withContext(Dispatchers.IO) {
        if (query != "") {
            val response = gitHubApi.searchUsers(query)
            if (response.isSuccessful) {
                response.body()?.let {
                    searchQueryDao.upsert(SearchQuery(query, System.currentTimeMillis()))
                    users = convertUserSchemaListToUserList(it.items)
                    return@withContext users
                }
            }
        }
        emptyList()
    }


    private fun convertUserSchemaListToUserList(userSchemas: List<UserSchema>): List<User> {
        val userList: MutableList<User> = mutableListOf()

        userSchemas.forEach { user ->
            userList.add(
                User(
                    id = user.id,
                    username = user.login,
                    name = user.name,
                    url = user.url
                )
            )
        }
        return userList
    }

}