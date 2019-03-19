package com.media.monks.albumlist.challenge.data.network.response

data class PhotosReponse(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)