package com.media.monks.albumlist.challenge.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.media.monks.albumlist.challenge.R
import com.media.monks.albumlist.challenge.data.db.entity.Photos
import com.media.monks.albumlist.challenge.ui.adapter.PhotosViewPagerAdapter
import com.media.monks.albumlist.challenge.viemodel.photos.PhotosViewModel
import com.media.monks.albumlist.challenge.viemodel.photos.PhotosViewModelFactory
import kotlinx.android.synthetic.main.fragment_photo_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory

class PhotoListFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    var photosViewPagerAdapter: PhotosViewPagerAdapter? = null

    private val photosViewModelFactory: ((Int) -> PhotosViewModelFactory) by factory()
    private lateinit var photosViewModel: PhotosViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { PhotoListFragmentArgs.fromBundle(it) }

        photosViewModel = ViewModelProviders.of(this, photosViewModelFactory(safeArgs?.albumId!!))
            .get(PhotosViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main){
        val photosResult = photosViewModel.fetchPhotosById.await()

        photosResult.observe(this@PhotoListFragment, Observer { result: List<Photos> ->
            if (result.isEmpty()) return@Observer
            showPhotos(result)
        })
    }

    private fun showPhotos(photos: List<Photos>){
        photosViewPagerAdapter = PhotosViewPagerAdapter(this.requireContext(), photos)
        photosViewPager.adapter = photosViewPagerAdapter
    }
}