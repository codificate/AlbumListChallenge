package com.media.monks.albumlist.challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.media.monks.albumlist.challenge.viemodel.album.AlbumViewModel
import com.media.monks.albumlist.challenge.viemodel.album.AlbumViewModelFactory
import com.media.monks.albumlist.challenge.viemodel.photos.PhotosViewModel
import com.media.monks.albumlist.challenge.viemodel.photos.PhotosViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

class SplashActivity : AppCompatActivity(), CoroutineScope, KodeinAware {
    override val kodein by closestKodein()
    private lateinit var job: Job

    private val albumViewModelFactory: AlbumViewModelFactory by instance()
    private lateinit var albumViewModel: AlbumViewModel

    private val photosViewModelFactory: PhotosViewModelFactory by instance()
    private lateinit var photosViewModel: PhotosViewModel

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        job = Job()

        albumViewModel = ViewModelProviders.of(this, albumViewModelFactory)
            .get(AlbumViewModel::class.java)

        photosViewModel = ViewModelProviders.of(this, photosViewModelFactory)
            .get(PhotosViewModel::class.java)

        initData()
    }

    private fun initData() = launch(Dispatchers.Main){
        val album = albumViewModel.initAlbums.await()
        val photo = photosViewModel.initPhotos.await()

        if (album.id!=null&&photo.id!=null){
            continueToMainActivity()
        }
    }

    private fun continueToMainActivity(){

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}
