package com.media.monks.albumlist.challenge

import android.app.Application
import com.media.monks.albumlist.challenge.data.db.AlbumDatabase
import com.media.monks.albumlist.challenge.data.network.*
import com.media.monks.albumlist.challenge.repository.AlbumPhotosRepository
import com.media.monks.albumlist.challenge.repository.AlbumPhotosRepositoryImpl
import com.media.monks.albumlist.challenge.viemodel.album.AlbumViewModelFactory
import com.media.monks.albumlist.challenge.viemodel.photos.PhotosViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.*

class AlbumListApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@AlbumListApplication))

        bind() from singleton { AlbumDatabase(instance()) }
        bind() from singleton { instance<AlbumDatabase>().albumDao() }
        bind() from singleton { instance<AlbumDatabase>().photoDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { AlbumApiService(instance()) }
        bind<AlbumPhotoDataSource>() with singleton { AlbumPhotoDataSourceImpl(instance()) }
        bind<AlbumPhotosRepository>() with singleton { AlbumPhotosRepositoryImpl(instance(), instance(), instance()) }
        bind() from provider { AlbumViewModelFactory(instance()) }
        bind() from factory { albumId: Int -> PhotosViewModelFactory(albumId, instance()) }
    }

    override fun onCreate() {
        super.onCreate()
    }
}