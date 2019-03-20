package com.media.monks.albumlist.challenge.viemodel.album

import androidx.lifecycle.ViewModel
import com.media.monks.albumlist.challenge.common.lazyDeferred
import com.media.monks.albumlist.challenge.repository.AlbumPhotosRepository

class AlbumViewModel(
    private val repository: AlbumPhotosRepository
) : ViewModel(){

    val initAlbums by lazyDeferred {
        repository.saveAllAlbums()
    }

    val fetchAlbums by lazyDeferred {
        repository.getAllAlbums()
    }
}