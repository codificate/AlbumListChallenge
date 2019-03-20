package com.media.monks.albumlist.challenge.ui.holder

import com.media.monks.albumlist.challenge.R
import com.media.monks.albumlist.challenge.data.db.entity.Album
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_album.*

class AlbumItem( val album: Album ) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            albumTitle.text = album.title
            characterAlbumTItle.text = album.title.capitalize().first().toString()
        }
    }

    override fun getLayout() = R.layout.item_album
}