package com.xabin.searchgithub.helper


import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.networking.repositories.RepositorySchema
import com.xabin.searchgithub.networking.users.UserSchema
import com.xabin.searchgithub.networking.users.UserSearchSchema
import com.xabin.searchgithub.testdata.UsersTestData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class EndpointTd: GitHubApi {

    var mTimesCalled = 0
    var mFailure = false

    override suspend fun searchUsers(username: String): Response<UserSearchSchema> {
        mTimesCalled++
        if (mFailure) {
            return Response.error(
                400,
                "{\"key\":[\"somestuff\"]}"
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
        }
        return Response.success(UserSearchSchema(2, false, UsersTestData.getUserSchemas()))
    }

    override suspend fun getUserProfile(username: String): Response<UserSchema?> {
        mTimesCalled++
        if (mFailure) {
            return Response.error(
                400,
                "{\"key\":[\"somestuff\"]}"
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
        }
        return Response.success(UsersTestData.getUserSchemas().first())
    }

    override suspend fun getFollowers(username: String): Response<List<UserSchema>> {
        mTimesCalled++
        if (mFailure) {
            return Response.error(
                400,
                "{\"key\":[\"somestuff\"]}"
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
        }
        return Response.success(UsersTestData.getUserSchemas())
    }

    override suspend fun getRepos(username: String): Response<List<RepositorySchema>> {
        mTimesCalled++
        if (mFailure) {
            return Response.error(
                400,
                "{\"key\":[\"somestuff\"]}"
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
        }
        return Response.success(UsersTestData.getRepositorySchemas())
    }


}