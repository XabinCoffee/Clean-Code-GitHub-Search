package com.xabin.searchgithub.networking.users

import com.google.gson.annotations.SerializedName

data class UserSearchSchema(
    @SerializedName("total_count") var totalCount: Long,
    @SerializedName("incomplete_results") var incompleteResults: Boolean,
    @SerializedName("items") var items: List<UserSchema>,
)