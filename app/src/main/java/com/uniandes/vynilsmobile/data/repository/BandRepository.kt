package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Band
import com.uniandes.vynilsmobile.data.service.RetrofitBroker

class BandRepository(val application: Application) {

    suspend fun getAllBands(): List<Band> {
        return try {
            var bands: List<Band> = emptyList()
            RetrofitBroker.getBands(
                onComplete = { response -> bands = response },
                onError = { error -> throw ApiRequestException(application.resources.getString(R.string.error_retrieve_bands), error) }
            )
            bands
        } catch (e: Throwable) {
            throw ApiRequestException("${R.string.error_retrieve_bands} -> ${e.cause}", e)
        }
    }

    suspend fun createBand(band: Band) = RetrofitBroker.createBand(band)
}
