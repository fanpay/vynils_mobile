package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import com.uniandes.vynilsmobile.data.model.Collector
import com.uniandes.vynilsmobile.data.service.RetrofitBroker

class CollectorRepository(val application: Application) {

    suspend fun getAllCollectors(): List<Collector> = RetrofitBroker.getAllCollectors()

}