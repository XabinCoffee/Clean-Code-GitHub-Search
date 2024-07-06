package com.xabin.searchgithub.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xabin.searchgithub.search_queries.SearchQuery

@Database(
    entities = [
        SearchQuery::class
    ],
    version = 1
)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract val searchQueryDao: SearchQueryDao
}