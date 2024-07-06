package com.xabin.searchgithub.users

data class Repository(
    var name: String = "",
    var htmlUrl: String = "",
    var description: String? = null,
    var language: String? = null
)