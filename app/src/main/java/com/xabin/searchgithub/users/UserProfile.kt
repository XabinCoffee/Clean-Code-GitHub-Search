package com.xabin.searchgithub.users

data class UserProfile(
    val id: Long,
    val username: String,
    val name: String?,
    val url: String,
    val followers: List<User>
)


