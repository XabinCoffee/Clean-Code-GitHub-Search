package com.xabin.searchgithub.testdata

import com.xabin.searchgithub.networking.users.UserSchema
import com.xabin.searchgithub.users.User
import com.xabin.searchgithub.users.UserProfile

class UsersTestData {
    companion object {

        fun getUserSchemas(): List<UserSchema> {
            return listOf(
                UserSchema(id = 1, login = "username1",  name ="user 1", url =  "url1", avatarUrl = "avatar1"),
                UserSchema(id = 2,login = "username2", name = "user 2", url = "url2", avatarUrl = "avatar2")
            )
        }

        fun getUsers(): List<User> {
            return listOf(
                User(1,"username1", "user 1", "url1", "avatar1"),
                User(2,"username2", "user 2", "url2", "avatar2")
            )
        }

        fun getUserProfile(): UserProfile {
            return UserProfile(1, "username1", "user 1", "url1", emptyList())
        }
    }
}