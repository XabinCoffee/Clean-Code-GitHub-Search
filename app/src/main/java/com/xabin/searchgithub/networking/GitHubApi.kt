package com.xabin.searchgithub.networking

import com.xabin.searchgithub.networking.users.UserSchema
import com.xabin.searchgithub.networking.users.UserSearchSchema
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("/search/users")
    suspend fun searchUsers(@Query("q") username: String): Response<UserSearchSchema>

    @GET("/users/{username}")
    suspend fun getUserProfile(@Path("username") username: String): Response<UserSchema?>
}