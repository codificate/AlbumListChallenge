package com.media.monks.albumlist.challenge.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.media.monks.albumlist.challenge.R
import com.media.monks.albumlist.challenge.common.glide.GlideApp
import com.media.monks.albumlist.challenge.data.db.entity.Photos

class PhotosViewPagerAdapter(
    val context: Context, val photos: List<Photos>) : PagerAdapter(){

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return photos.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater
            .from(context).inflate(R.layout.photo_item,container,false)

        val img: ImageView = view.findViewById(R.id.photoAlbum)
        val ttl: TextView = view.findViewById(R.id.photoTitle)

        setImage(img, position)
        setTitle(ttl, position)

        container.addView(view)
        return view
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    private fun setImage(imageView: ImageView, position: Int) {
        GlideApp.with(context)
            .load(photos[position].url)
            .into(imageView)
    }

    private fun setTitle(textView: TextView, position: Int){
        textView.text = photos[position].title
    }
}