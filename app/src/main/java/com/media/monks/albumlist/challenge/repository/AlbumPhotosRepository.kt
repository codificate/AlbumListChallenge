package com.media.monks.albumlist.challenge.repository

import androidx.lifecycle.LiveData
import com.media.monks.albumlist.challenge.data.db.entity.Album
import com.media.monks.albumlist.challenge.data.db.entity.Photos

interface AlbumPhotosRepository {
    suspend fun getAllAlbums(): LiveData<List<Album>>
    suspend fun getPhotosByAlbumId(id: Int): LiveData<List<Photos>>
}