package com.example.paging3tutorial.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paging3tutorial.data.local.dao.RemoteKeysDao
import com.example.paging3tutorial.data.local.dao.ResultEntityDao
import com.example.paging3tutorial.data.local.entity.RemoteKeys
import com.example.paging3tutorial.data.local.entity.ResultEntity

@Database(entities = [ResultEntity::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class RickyDb : RoomDatabase() {
    abstract fun reposDao(): ResultEntityDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}