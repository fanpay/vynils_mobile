package com.uniandes.vynilsmobile.data.service

import android.util.Log
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.model.Artist
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

        // Artists
        suspend fun getArtists(
            onComplete: (resp: List<Artist>) -> Unit,
            onError: (error: Throwable) -> Unit
        ) {
            try {
                val response = ApiClient.artists.getAllArtists()
                if (response.isSuccessful) {
                    onComplete(response.body() ?: emptyList())
                } else {
                    onError(Exception("Error en la solicitud a la API: ${response.code()}"))
                }
            } catch (e: Throwable) {
                onError(e)
            }
        }

        suspend fun createArtist(artist: Artist): Artist? {
            val request = ApiClient.artists.createArtist(artist)
            return if (request.isSuccessful) {
                Log.e("SuccessCrearArtist", request.toString())
                request.body()
            } else {
                Log.e("ErrorCrearArtist", request.toString())
                null
            }
        }

        // Collectors
        suspend fun getCollectors(
            onComplete: (resp: List<Collector>) -> Unit,
            onError: (error: Throwable) -> Unit
        ) {
            try {
                val response = ApiClient.collectors.getAllCollectors()
                if (response.isSuccessful) {
                    onComplete(response.body() ?: emptyList())
                } else {
                    onError(Exception("Error en la solicitud a la API: ${response.code()}"))
                }
            } catch (e: Throwable) {
                onError(e)
            }
        }

        suspend fun createCollector(collector: Collector): Collector? {
            val request = ApiClient.collectors.createCollector(collector)
            return if (request.isSuccessful) {
                Log.e("SuccessCrearCollector", request.toString())
                request.body()
            } else {
                Log.e("ErrorCrearCollector", request.toString())
                null
            }
        }

        fun getAllComments (): List<Comment> {
            return listOf()
        }

    }

}