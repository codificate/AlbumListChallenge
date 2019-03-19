package com.media.monks.albumlist.challenge.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
class Photos(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val albumId: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)