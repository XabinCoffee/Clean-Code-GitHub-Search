package com.xabin.searchgithub.networking.repositories

import com.google.gson.annotations.SerializedName

data class RepositorySchema(
    @SerializedName("name") var name: String = "",
    @SerializedName("html_url") var htmlUrl: String = "",
    @SerializedName("description") var description: String? = null,
    @SerializedName("language") var language: String? = null
)