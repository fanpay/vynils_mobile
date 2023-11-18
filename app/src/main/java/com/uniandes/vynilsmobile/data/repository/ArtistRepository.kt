package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Artist
import com.uniandes.vynilsmobile.data.service.RetrofitBroker

class ArtistRepository(val application: Application) {

    suspend fun getAllArtists(): List<Artist> {
        return try {
            var artists: List<Artist> = emptyList()
            RetrofitBroker.getArtists(
                onComplete = { response -> artists = response },
                onError = { error -> throw ApiRequestException(application.resources.getString(R.string.error_retrieve_albums), error) }
            )
            artists
        } catch (e: Throwable) {
            throw ApiRequestException("${R.string.error_retrieve_albums} -> ${e.cause}", e)
        }
    }

}