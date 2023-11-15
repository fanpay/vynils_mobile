package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Collector
import com.uniandes.vynilsmobile.data.service.RetrofitBroker

class CollectorRepository(val application: Application) {
    suspend fun getAllCollectors(): List<Collector> {
        return try {
            var collectors: List<Collector> = emptyList()
            RetrofitBroker.getCollectors(
                onComplete = { response -> collectors = response },
                onError = { error -> throw ApiRequestException(application.resources.getString(R.string.error_retrieve_collectors), error) }
            )
            collectors
        } catch (e: Throwable) {
            throw ApiRequestException("${R.string.error_retrieve_collectors} -> ${e.cause}", e)
        }
    }
    suspend fun createCollector(collector: Collector) = RetrofitBroker.createCollector(collector)
}