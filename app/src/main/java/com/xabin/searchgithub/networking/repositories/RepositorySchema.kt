package com.xabin.searchgithub.networking.repositories

import com.google.gson.annotations.SerializedName
import com.xabin.searchgithub.users.Repository

data class RepositorySchema(
    @SerializedName("name") var name: String = "",
    @SerializedName("html_url") var htmlUrl: String = "",
    @SerializedName("description") var description: String? = null,
    @SerializedName("language") var language: String? = null
) {
    fun toDomain(): Repository = Repository(
        name = name,
        htmlUrl = htmlUrl,
        description = description,
        language = language
    )
}