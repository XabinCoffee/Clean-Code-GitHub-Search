package com.xabin.searchgithub.search_queries

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_query")
data class SearchQuery(
    @ColumnInfo(name = "query") @PrimaryKey val query: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)