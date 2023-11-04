package com.uniandes.vynilsmobile.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.databinding.AlbumItemBinding
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.view.AlbumFragmentDirections

class AlbumsAdapter(private val progressBar: ProgressBar) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>(){

    private val asyncListDiffer = AsyncListDiffer(this, AlbumDiffCallback())

    var albums: List<Album>
        get() = asyncListDiffer.currentList
        set(value) {
            asyncListDiffer.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = asyncListDiffer.currentList[position]
        holder.bind(album)

        if (itemCount > 0) {
            progressBar.visibility = View.GONE
        } else {
            progressBar.visibility = View.VISIBLE
        }

        holder.loadAlbumCover(album.cover)
    }

    override fun getItemCount(): Int {
        return albums.size
    }


    class AlbumViewHolder(private val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }

        fun bind(album: Album) {
            viewDataBinding.album = album
            viewDataBinding.root.setOnClickListener {
                val action = AlbumFragmentDirections.actionAlbumFragmentToAlbumDetailFragment(album)
                viewDataBinding.root.findNavController().navigate(action)
            }
        }

        fun loadAlbumCover(imageUrl: String) {

            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_baseline_album_24)
                .error(R.drawable.ic_baseline_android_24)
                .into(viewDataBinding.imageView1, object : Callback {
                    override fun onSuccess() {
                        return
                    }

                    override fun onError(e: Exception?) {
                        Log.e("Picasso Error", "Error al cargar la imagen: ${e?.message}")
                        return
                    }
                })
        }
    }
    private class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.albumId == newItem.albumId
        }
        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }
}