package com.media.monks.albumlist.challenge.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.media.monks.albumlist.challenge.R
import com.media.monks.albumlist.challenge.data.toAlbumViewHolder
import com.media.monks.albumlist.challenge.ui.holder.AlbumItem
import com.media.monks.albumlist.challenge.viemodel.album.AlbumViewModel
import com.media.monks.albumlist.challenge.viemodel.album.AlbumViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_album_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class AlbumListFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val albumViewModelFactory: AlbumViewModelFactory by instance()
    private lateinit var albumViewModel: AlbumViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_album_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        albumViewModel = ViewModelProviders.of(this, albumViewModelFactory)
            .get(AlbumViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main){
        val albums = albumViewModel.fetchAlbums.await()

        albums.observe(this@AlbumListFragment, Observer { albumList ->
            if (albumList.isEmpty()) return@Observer

            group_loading.visibility = GONE
            initRecyclerView(albumList.toAlbumViewHolder())
        })
    }

    private fun initRecyclerView(items: List<AlbumItem>){
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@AlbumListFragment.context)
            adapter = groupAdapter
        }
    }
}