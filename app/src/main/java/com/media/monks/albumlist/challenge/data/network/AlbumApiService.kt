package com.media.monks.albumlist.challenge.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.media.monks.albumlist.challenge.BuildConfig
import com.media.monks.albumlist.challenge.data.network.response.AlbumsReponse
import com.media.monks.albumlist.challenge.data.network.response.PhotosReponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface AlbumApiService {

    @GET(BuildConfig.ALBUMS)
    fun getAlbums(): Deferred<List<AlbumsReponse>>

    @Headers("Cache-Control: public, max-stale=2419200")
    @GET(BuildConfig.PHOTOS)
    fun getPhotos(): Deferred<List<PhotosReponse>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): AlbumApiService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AlbumApiService::class.java)
        }
    }
}