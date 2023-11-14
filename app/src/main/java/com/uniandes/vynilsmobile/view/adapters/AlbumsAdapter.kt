package com.uniandes.vynilsmobile.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.databinding.AlbumItemBinding

class AlbumsAdapter(private val progressBar: ProgressBar, private val onItemClick: (Album) -> Unit) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>(){

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
        holder.bind(album, onItemClick)

        progressBar.visibility = if(itemCount > 0) View.GONE else View.VISIBLE
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

        fun bind(album: Album, onItemClick: (Album) -> Unit) {
            viewDataBinding.album = album
            viewDataBinding.root.setOnClickListener {
                onItemClick(album)
            }
            loadAlbumCover(album.cover)
        }

        private fun loadAlbumCover(imageUrl: String) {
            Picasso.get()
                .load(imageUrl.toUri())
                .placeholder(R.drawable.ic_baseline_album_24)
                .error(R.drawable.ic_baseline_android_24)
                .fit()
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(viewDataBinding.imageView1, object : Callback {
                    override fun onSuccess() {
                        return
                    }
                    override fun onError(e: Exception?) {
                       Picasso.get()
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_baseline_album_24)
                            .error(R.drawable.ic_baseline_android_24)
                            .fit()
                            .centerCrop()
                            .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                            .into(viewDataBinding.imageView1, object : Callback {
                                override fun onSuccess() {
                                    return
                                }
                                override fun onError(e: Exception?) {
                                    Log.e("Picasso Error", "Error al cargar la imagen: ${e?.message}")
                                    viewDataBinding.imageView1.setImageResource(R.drawable.ic_baseline_android_24)
                                    e?.printStackTrace()
                                }
                            })
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