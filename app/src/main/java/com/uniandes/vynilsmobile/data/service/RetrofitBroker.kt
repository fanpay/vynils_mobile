package com.uniandes.vynilsmobile.data.service

import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.model.Artist
import com.uniandes.vynilsmobile.data.model.Collector

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
    }

}