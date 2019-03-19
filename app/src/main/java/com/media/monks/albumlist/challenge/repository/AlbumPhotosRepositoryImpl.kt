package com.media.monks.albumlist.challenge.repository

import androidx.lifecycle.LiveData
import com.media.monks.albumlist.challenge.data.db.dao.AlbumDao
import com.media.monks.albumlist.challenge.data.db.dao.PhotoDao
import com.media.monks.albumlist.challenge.data.db.entity.Album
import com.media.monks.albumlist.challenge.data.db.entity.Photos
import com.media.monks.albumlist.challenge.data.network.AlbumPhotoDataSource
import com.media.monks.albumlist.challenge.data.toAlbumEntity
import com.media.monks.albumlist.challenge.data.toPhotosEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumPhotosRepositoryImpl(
    private val albumDao: AlbumDao,
    private val photoDao: PhotoDao,
    private val albumPhotoDataSource: AlbumPhotoDataSource
) : AlbumPhotosRepository {

    init {
        albumPhotoDataSource.apply {
            downloadedAlbums.observeForever { albums -> persistAlbums(albums.toAlbumEntity()) }
            downloadedPhotos.observeForever { photos -> persistPhotos(photos.toPhotosEntity()) }
        }
    }

    override suspend fun getAllAlbums(): LiveData<List<Album>> {
        return withContext(Dispatchers.IO) {
            fetchAlbums()
            return@withContext albumDao.getAll()
        }
    }

    override suspend fun saveAllPhotos(): Photos{
        return withContext(Dispatchers.IO) {
            fetchPhotos()
            return@withContext photoDao.getFirst()
        }
    }

    override suspend fun getPhotosByAlbumId(id: Int): LiveData<List<Photos>> {
        return withContext(Dispatchers.IO) {
            return@withContext photoDao.getByAlbumId(id)
        }
    }

    private fun persistAlbums(albumList: List<Album>){
        GlobalScope.launch(Dispatchers.IO){
            albumDao.bulk(albumList)
        }
    }

    private fun persistPhotos(photosList: List<Photos>){
        GlobalScope.launch(Dispatchers.IO){
            photoDao.bulk(photosList)
        }
    }

    private suspend fun fetchAlbums(){
        val album = albumDao.getFirst()
        if (album.id==null){
            albumPhotoDataSource.fetchAlbums()
        }
    }

    private suspend fun fetchPhotos(){
        val album = photoDao.getFirst()
        if (album.id==null){
            albumPhotoDataSource.fetchPhotos()
        }
    }
}