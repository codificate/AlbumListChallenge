package com.media.monks.albumlist.challenge.data

import com.media.monks.albumlist.challenge.data.db.entity.Album
import com.media.monks.albumlist.challenge.data.db.entity.Photos
import com.media.monks.albumlist.challenge.data.network.response.AlbumsReponse
import com.media.monks.albumlist.challenge.data.network.response.PhotosReponse
import com.media.monks.albumlist.challenge.ui.holder.AlbumItem

fun List<AlbumsReponse>.toAlbumEntity(): List<Album>{
    return this.map { Album(it.id, it.title, it.userId) }
}

fun List<PhotosReponse>.toPhotosEntity(): List<Photos>{
    return this.map { Photos(it.id, it.albumId, it.thumbnailUrl, it.title, it.title) }
}

fun List<Album>.toAlbumViewHolder(): List<AlbumItem>{
    return this.map { AlbumItem(it) }
}