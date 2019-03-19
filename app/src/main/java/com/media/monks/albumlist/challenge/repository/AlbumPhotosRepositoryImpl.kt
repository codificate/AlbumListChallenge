package com.media.monks.albumlist.challenge.repository

import androidx.lifecycle.LiveData
import com.media.monks.albumlist.challenge.data.db.dao.AlbumDao
import com.media.monks.albumlist.challenge.data.db.dao.PhotoDao
import com.media.monks.albumlist.challenge.data.db.entity.Album
import com.media.monks.albumlist.challenge.data.db.entity.Photos
import com.media.monks.albumlist.challenge.data.network.AlbumPhotoDataSource

class AlbumPhotosRepositoryImpl(
    private val albumDao: AlbumDao,
    private val photoDao: PhotoDao,
    private val albumPhotoDataSource: AlbumPhotoDataSource
) : AlbumPhotosRepository {

    init {
        albumPhotoDataSource.apply {
            downloadedAlbums.observeForever {  }
            downloadedPhotos.observeForever {  }
        }
    }

    override suspend fun getAllAlbums(): LiveData<List<Album>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private suspend fun fetchAlbums(){
        val album = albumDao.getFirst()
        if (album.id==null){
            albumPhotoDataSource.fetchAlbums()
        }
    }

    override suspend fun getPhotosByAlbumId(id: Int): LiveData<List<Photos>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private suspend fun fetchPhotos(){
        val album = photoDao.getFirst()
        if (album.id==null){
            albumPhotoDataSource.fetchPhotos()
        }
    }
}