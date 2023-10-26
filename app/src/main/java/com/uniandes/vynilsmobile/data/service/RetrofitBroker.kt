package com.uniandes.vynilsmobile.data.service

import android.util.Log
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.model.Collector
import com.uniandes.vynilsmobile.data.model.Comment


class RetrofitBroker {

    companion object {

        // Albums
        suspend fun getAllAlbums (): List<Album> {
            val request = ApiClient.albums.getAllAlbums()
            return if (request.isSuccessful)
                request.body() ?: listOf()
            else
                listOf()
        }

        suspend fun createAlbum(album: Album): Album? {
            val request = ApiClient.albums.createAlbum(album)
            if (request.isSuccessful){
                Log.e("SuccessCrearAlbum", request.toString())
                return request.body()
            }else{
                Log.e("ErrorCrearAlbum", request.toString())
                return null
            }
        }

        // Collectors
        suspend fun getAllCollectors (): List<Collector> {
            val request = ApiClient.collectors.getAllCollectors()
            return if (request.isSuccessful)
                request.body() ?: listOf()
            else
                listOf()
        }

        suspend fun getAllComments (): List<Comment> {
            return listOf()
        }

    }

}