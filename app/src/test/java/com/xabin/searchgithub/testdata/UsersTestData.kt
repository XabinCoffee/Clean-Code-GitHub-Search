package com.xabin.searchgithub.testdata

import com.xabin.searchgithub.networking.repositories.RepositorySchema
import com.xabin.searchgithub.networking.users.UserSchema
import com.xabin.searchgithub.users.Repository
import com.xabin.searchgithub.users.User

class UsersTestData {
    companion object {

        fun getUserSchemas(): List<UserSchema> {
            return listOf(
                UserSchema(id = 1, login = "username1",  name ="user 1", url =  "url1", avatarUrl = "avatar1", bio = "bio1", followers = 0,  following = 0,  publicRepos = 0),
                UserSchema(id = 2,login = "username2", name = "user 2", url = "url2", avatarUrl = "avatar2", bio = "bio2", followers = 0,  following = 0,  publicRepos = 0)
            )
        }

        fun getUsers(): List<User> {
            return listOf(
                User(1,"username1", "user 1", "url1", "avatar1", bio = "bio1", followers = 0,  following = 0,  publicRepos = 0),
                User(2,"username2", "user 2", "url2", "avatar2", bio = "bio2", followers = 0,  following = 0,  publicRepos = 0)
            )
        }

        fun getUser(): User {
            return User(1, "username1", "user 1", "url1", "avatar1", "bio1", 0, 0, 0)
        }

        fun getRepositorySchemas(): List<RepositorySchema> {
            return listOf(
                RepositorySchema("repo", "htmlUrl", "desc", "lang"),
                RepositorySchema("repo2", "htmlUrl2", "desc2", "lang2")
            )
        }

        fun getRepositories(): List<Repository> {
            return listOf(
                Repository("repo", "htmlUrl", "desc", "lang"),
                Repository("repo2", "htmlUrl2", "desc2", "lang2")
            )
        }
    }
}