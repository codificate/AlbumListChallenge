package com.media.monks.albumlist.challenge.viemodel.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.media.monks.albumlist.challenge.repository.AlbumPhotosRepository

class PhotosViewModelFactory(
    private val repository: AlbumPhotosRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhotosViewModel(repository) as T
    }
}