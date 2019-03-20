package com.media.monks.albumlist.challenge.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.media.monks.albumlist.challenge.data.db.dao.AlbumDao
import com.media.monks.albumlist.challenge.data.db.dao.PhotoDao
import com.media.monks.albumlist.challenge.data.db.entity.Album
import com.media.monks.albumlist.challenge.data.db.entity.Photos

@Database(entities = [Album::class, Photos::class], version = 1, exportSchema = true)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile private var instance: AlbumDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AlbumDatabase::class.java, "carSaleEntries.db")
                .build()
    }
}