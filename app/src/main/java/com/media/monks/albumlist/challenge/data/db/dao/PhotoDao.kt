package com.media.monks.albumlist.challenge.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.media.monks.albumlist.challenge.data.db.entity.Photos

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulk(photoList:List<Photos>)

    @Query("select p.* from photos p inner join album a on p.albumId=a.id where p.albumId = :id")
    fun getByAlbumId(id: Int): LiveData<List<Photos>>

    @Query("select * from photos limit 1")
    fun getFirst(): Photos

}