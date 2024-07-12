package com.xabin.searchgithub.users

data class User(
    val id: Long = 1,
    val username: String = "",
    val name: String? = null,
    val url: String = "",
    val avatarUrl: String = "",
    val bio: String = "",
    val followers: Int = 0,
    val following: Int = 0,
    val publicRepos: Int = 0
)


