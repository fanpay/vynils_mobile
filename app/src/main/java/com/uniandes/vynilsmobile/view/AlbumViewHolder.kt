package com.uniandes.vynilsmobile.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uniandes.vynilsmobile.databinding.ItemAlbumBinding

class AlbumViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemAlbumBinding.bind(view)
    fun bind(image:String){
        Picasso.get().load(image).into(binding.ivAlbum)

    }
}