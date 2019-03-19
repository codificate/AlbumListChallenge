package com.media.monks.albumlist.challenge.data.network

import androidx.lifecycle.LiveData
import com.media.monks.albumlist.challenge.data.network.response.AlbumsReponse
import com.media.monks.albumlist.challenge.data.network.response.PhotosReponse

interface AlbumPhotoDataSource {

    val downloadedAlbums: LiveData<List<AlbumsReponse>>
    val downloadedPhotos: LiveData<List<PhotosReponse>>

    suspend fun fetchAlbums()
    suspend fun fetchPhotos()

}