package com.uniandes.vynilsmobile.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vynilsmobile.R

class AlbumAdapter (private val albums:List<Album>, private val onClickListener:(Album) -> Unit):RecyclerView.Adapter<AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AlbumViewHolder(layoutInflater.inflate(R.layout.item_album,parent, false))

    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.render(albums[position], onClickListener)
    }
}