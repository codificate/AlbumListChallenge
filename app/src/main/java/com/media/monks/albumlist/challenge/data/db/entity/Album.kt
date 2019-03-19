package com.media.monks.albumlist.challenge.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class Album(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val title: String,
    val userId: Int
)