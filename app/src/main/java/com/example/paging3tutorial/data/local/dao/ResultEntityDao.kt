package com.example.paging3tutorial.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paging3tutorial.data.local.entity.ResultEntity

@Dao
interface ResultEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<ResultEntity>)

    @Query(
        "SELECT * FROM result_entity"
    )
    fun reposByName(): PagingSource<Int, ResultEntity>

    @Query("DELETE FROM result_entity")
    suspend fun clearRepos()

}