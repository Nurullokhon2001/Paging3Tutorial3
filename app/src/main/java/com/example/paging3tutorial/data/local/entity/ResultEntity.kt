package com.example.paging3tutorial.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paging3tutorial.data.network.dto.LocationDto
import com.example.paging3tutorial.data.network.dto.OriginDto

@Entity(tableName = "result_entity")
data class ResultEntity(
    val created: String,
    val gender: String,
    @PrimaryKey  val id: Int,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)