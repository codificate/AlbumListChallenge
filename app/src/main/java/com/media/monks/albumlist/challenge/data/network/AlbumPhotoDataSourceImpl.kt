package com.media.monks.albumlist.challenge.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.media.monks.albumlist.challenge.common.NoConnectivityException
import com.media.monks.albumlist.challenge.data.network.response.AlbumsReponse
import com.media.monks.albumlist.challenge.data.network.response.PhotosReponse

class AlbumPhotoDataSourceImpl(
    private val albumApiService: AlbumApiService
) : AlbumPhotoDataSource {

    private val _downloadedAlbums = MutableLiveData<List<AlbumsReponse>>()
    override val downloadedAlbums: LiveData<List<AlbumsReponse>>
        get() = _downloadedAlbums

    private val _downloadedPhotos = MutableLiveData<List<PhotosReponse>>()
    override val downloadedPhotos: LiveData<List<PhotosReponse>>
        get() = _downloadedPhotos

    override suspend fun fetchAlbums(){
        try {
            val albumsFetched = albumApiService.getAlbums().await()
            _downloadedAlbums.postValue(albumsFetched)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

    override suspend fun fetchPhotos(){
        try {
            val photosFetched = albumApiService.getPhotos().await()
            _downloadedPhotos.postValue(photosFetched)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

}