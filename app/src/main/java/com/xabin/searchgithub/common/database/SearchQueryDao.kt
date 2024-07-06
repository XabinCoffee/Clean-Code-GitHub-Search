package com.xabin.searchgithub.common.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.xabin.searchgithub.search_queries.SearchQuery

@Dao
interface SearchQueryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(searchQuery: SearchQuery)
}
