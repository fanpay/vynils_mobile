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
import com.uniandes.vynilsmobile.data.model.Artist
import com.uniandes.vynilsmobile.databinding.ArtistItemBinding

class ArtistsAdapter(private val progressBar: ProgressBar, private val onItemClick: (Artist) -> Unit) : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>(){

    private val asyncListDiffer = AsyncListDiffer(this, ArtistDiffCallback())

    var artists: List<Artist>
        get() = asyncListDiffer.currentList
        set(value) {
            asyncListDiffer.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val withDataBinding: ArtistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistViewHolder.LAYOUT,
            parent,
            false)
        return ArtistViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = asyncListDiffer.currentList[position]
        holder.bind(artist)

        holder.viewDataBinding.artist = artist
        holder.viewDataBinding.root.setOnClickListener {
            onItemClick(artist)
        }

        progressBar.visibility = if(itemCount > 0) View.GONE else View.VISIBLE

    }

    override fun getItemCount(): Int {
        return artists.size
    }


    class ArtistViewHolder(val viewDataBinding: ArtistItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_item
        }

        fun bind(artist: Artist) {
            Picasso.get()
                .load(artist.image.toUri().buildUpon().scheme("https").build())
                .placeholder(R.drawable.ic_baseline_person_24)
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
                            .load(artist.image.toUri().buildUpon().scheme("https").build())
                            .placeholder(R.drawable.ic_baseline_person_24)
                            .error(R.drawable.ic_baseline_android_24)
                            .fit()
                            .centerCrop()
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
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
    private class ArtistDiffCallback : DiffUtil.ItemCallback<Artist>() {
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem.artistId == newItem.artistId
        }
        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem == newItem
        }
    }
}