package com.media.monks.albumlist.challenge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.media.monks.albumlist.challenge.ui.MainActivity
import com.media.monks.albumlist.challenge.viemodel.album.AlbumViewModel
import com.media.monks.albumlist.challenge.viemodel.album.AlbumViewModelFactory
import com.media.monks.albumlist.challenge.viemodel.photos.PhotosViewModel
import com.media.monks.albumlist.challenge.viemodel.photos.PhotosViewModelFactory
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext


class SplashActivity : AppCompatActivity(), CoroutineScope, KodeinAware {
    override val kodein by closestKodein()
    private lateinit var job: Job

    private var albumProcessFinished: Boolean = false
    private var photoProcessFinished: Boolean = false

    private val albumViewModelFactory: AlbumViewModelFactory by instance()
    private lateinit var albumViewModel: AlbumViewModel

    private val photosViewModelFactory: ((Int) -> PhotosViewModelFactory) by factory()
    private lateinit var photosViewModel: PhotosViewModel

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        job = Job()

        albumViewModel = ViewModelProviders.of(this, albumViewModelFactory)
            .get(AlbumViewModel::class.java)

        photosViewModel = ViewModelProviders.of(this, photosViewModelFactory(0))
            .get(PhotosViewModel::class.java)

        isFirstRun()
    }

    private fun isFirstRun(){
        val isFirstRun = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            .getBoolean("isFirstRun", true)

        if (isFirstRun) {
            getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply()
            initData()
        } else {
            continueToMainActivity()
        }
    }

    private fun initData() = launch(Dispatchers.Main){

        val album = albumViewModel.initAlbums.await()
        val photos= photosViewModel.initPhotos.await()

        album.observe(this@SplashActivity, Observer {album ->
            if (album==null) return@Observer
            albumProcessFinished = true
            ifInitDataIsDone()
        })

        photos.observe(this@SplashActivity, Observer {photos ->
            if (photos==null) return@Observer
            photoProcessFinished = true
            ifInitDataIsDone()
        })
    }

    private fun ifInitDataIsDone(){
        if (albumProcessFinished && photoProcessFinished){
            albumProcessFinished = false
            photoProcessFinished = false
            group_loading.visibility = GONE
            job.cancel()
            continueToMainActivity()
        }
    }

    private fun continueToMainActivity(){
        val intentToContinue = Intent(applicationContext, MainActivity::class.java)
        intentToContinue.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intentToContinue)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
