package com.uniandes.vynilsmobile.data.service

import android.util.Log
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.model.Band
import com.uniandes.vynilsmobile.data.model.Collector
import com.uniandes.vynilsmobile.data.model.Comment

class RetrofitBroker {

    companion object {

        // Albums
        suspend fun getAlbums(
            onComplete: (resp: List<Album>) -> Unit,
            onError: (error: Throwable) -> Unit
        ) {
            try {
                val response = ApiClient.albums.getAllAlbums()
                if (response.isSuccessful) {
                    onComplete(response.body() ?: emptyList())
                } else {
                    onError(Exception("Error en la solicitud a la API: ${response.code()}"))
                }
            } catch (e: Throwable) {
                onError(e)
            }
        }

        suspend fun createAlbum(album: Album): Album? {
            val request = ApiClient.albums.createAlbum(album)
            return if (request.isSuccessful){
                Log.e("SuccessCrearAlbum", request.toString())
                request.body()
            }else{
                Log.e("ErrorCrearAlbum", request.toString())
                null
            }
        }

        // Bands
        suspend fun getBands(
            onComplete: (resp: List<Band>) -> Unit,
            onError: (error: Throwable) -> Unit
        ) {
            try {
                val response = ApiClient.bands.getAllBands()
                if (response.isSuccessful) {
                    onComplete(response.body() ?: emptyList())
                } else {
                    onError(Exception("Error en la solicitud a la API: ${response.code()}"))
                }
            } catch (e: Throwable) {
                onError(e)
            }
        }

        suspend fun createBand(band: Band): Band? {
            val request = ApiClient.bands.createBand(band)
            return if (request.isSuccessful) {
                Log.e("SuccessCrearBand", request.toString())
                request.body()
            } else {
                Log.e("ErrorCrearBand", request.toString())
                null
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