package com.uniandes.vynilsmobile.data.service

import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.model.Artist
import com.uniandes.vynilsmobile.data.model.Collector
import com.uniandes.vynilsmobile.data.model.Comment
import com.uniandes.vynilsmobile.data.model.Prize

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
                    onError(Exception("getAlbums -> Error en la solicitud a la API: ${response.code()} - ${response.raw()} - ${response.body()}"))
                }
            } catch (e: Throwable) {
                onError(e)
            }
        }

        suspend fun createAlbum(album: Album,
                                onComplete: (resp: Album) -> Unit,
                                onError: (error: Throwable) -> Unit) {
            try {
                val response = ApiClient.albums.createAlbum(album)
                if (response.isSuccessful) {
                    response.body()?.let { onComplete(it) }
                } else {
                    onError(Exception("createAlbum -> Error en la solicitud a la API: ${response.code()} - ${response.raw()} - ${response.body()}"))
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
                    onError(Exception("Error en la solicitud a la API: ${response.code()} - ${response.raw()} - ${response.body()}"))
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
                    onError(Exception("getCollectors -> Error en la solicitud a la API: ${response.code()} - ${response.raw()} - ${response.body()}"))
                }
            } catch (e: Throwable) {
                onError(e)
            }
        }

        // Prizes
        suspend fun createPrize(prize: Prize,
                                onComplete: (resp: Prize) -> Unit,
                                onError: (error: Throwable) -> Unit) {
            try {
                val response = ApiClient.prizes.createPrize(prize)
                if (response.isSuccessful) {
                    response.body()?.let { onComplete(it) }
                } else {
                    onError(Exception("createPrize -> Error en la solicitud a la API: ${response.code()} - ${response.raw()} - ${response.body()}"))
                }
            } catch (e: Throwable) {
                onError(e)
            }
        }
        // Comentarios
        suspend fun addComment(
                albumId:Int,
                comment: Comment,
                onComplete: (resp: Comment) -> Unit,
                onError: (error: Throwable) -> Unit
        ) {
             try {
                 val response = ApiClient.comments.addComment(albumId, comment)
                 if (response.isSuccessful) {
                     response.body()?.let { onComplete(it) }
                 } else {
                     onError(Exception("addComment -> Error en la solicitud a la API: ${response.code()} - ${response.raw()} - ${response.body()}"))
                 }
             } catch (e: Throwable) {
                 onError(e)
            }
        }
    }
}
