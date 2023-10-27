package com.uniandes.vynilsmobile.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uniandes.vynilsmobile.databinding.ItemAlbumBinding

class AlbumViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemAlbumBinding.bind(view)
    fun render(album:Album, onClickListener:(Album) -> Unit){
        Picasso.get().load(album.cover).into(binding.ivAlbum)
        binding.tvAlbumName.text = album.name

        itemView.setOnClickListener{onClickListener(album)}

    }
}