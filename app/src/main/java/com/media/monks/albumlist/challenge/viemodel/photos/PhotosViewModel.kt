package com.media.monks.albumlist.challenge.viemodel.photos

import androidx.lifecycle.ViewModel
import com.media.monks.albumlist.challenge.common.lazyDeferred
import com.media.monks.albumlist.challenge.repository.AlbumPhotosRepository

class PhotosViewModel(
    private val albumId: Int,
    private val repository: AlbumPhotosRepository
) : ViewModel() {

    val initPhotos by lazyDeferred {
        repository.saveAllPhotos()
    }

    val fetchPhotosById by lazyDeferred {
        repository.getPhotosByAlbumId(albumId)
    }
}