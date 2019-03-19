package com.media.monks.albumlist.challenge.viemodel.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.media.monks.albumlist.challenge.repository.AlbumPhotosRepository

class AlbumViewModelFactory(
    private val repository: AlbumPhotosRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumViewModel(repository) as T
    }
}