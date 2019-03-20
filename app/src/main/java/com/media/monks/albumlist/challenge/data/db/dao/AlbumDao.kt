package com.media.monks.albumlist.challenge.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.media.monks.albumlist.challenge.data.db.entity.Album

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulk(albumList: List<Album>)

    @Query("select * from album")
    fun getAll(): LiveData<List<Album>>

    @Query("select * from album limit 1")
    fun getFirst(): LiveData<Album>

}