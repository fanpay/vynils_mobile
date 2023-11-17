package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.gson.Gson
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.database.AlbumsDao
import com.uniandes.vynilsmobile.data.database.CollectorsDao
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.model.Collector
import com.uniandes.vynilsmobile.data.service.RetrofitBroker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorRepository(val application: Application, private val collectorsDao: CollectorsDao) {
    suspend fun getAllCollectors(): List<Collector> {
        return withContext(Dispatchers.IO) {

            try {
                val cached = collectorsDao.getCollectors()

                if (!isNetworkAvailable() && cached.isNotEmpty()) {
                    Log.v("CollectorRepository", "No Internet. Retrieving cached collectors. ${cached.size} found.")
                    return@withContext cached
                }

                if (!isNetworkAvailable() && cached.isEmpty()) {
                    Log.v("CollectorRepository", "No Internet. No cached data was found. Retrieving empty list.")
                    return@withContext cached
                }

                if (cached.isNotEmpty()) {
                    Log.v("CollectorRepository", "Retrieving cached collectors. ${cached.size} found.")
                    return@withContext cached
                }

                var collectors: List<Collector> = emptyList()

                RetrofitBroker.getCollectors(
                    onComplete = { response ->
                        collectors = response

                        CoroutineScope(Dispatchers.IO).launch {
                            insertCollectorsIntoDatabase(collectors)
                        }
                    },
                    onError = { error ->
                        throw ApiRequestException(
                            application.resources.getString(
                                R.string.error_retrieve_collectors
                            ), error
                        )
                    }
                )

                return@withContext collectors
            } catch (e: Throwable) {
                Log.e("CollectorRepository", "Error retrieving collectors from API: ${e.message}")
                throw ApiRequestException(
                    application.resources.getString(R.string.error_retrieve_collectors),
                    e
                )
            }
        }
    }


    private suspend fun isNetworkAvailable(): Boolean = withContext(Dispatchers.IO) {
        val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        val capabilities = cm.getNetworkCapabilities(network)
        return@withContext capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private suspend fun insertCollectorsIntoDatabase(collectors: List<Collector>) {
        collectorsDao.insertAll(collectors)
        Log.v("CollectorRepository", "Inserted ${collectors.size} collectors into the local database.")
    }

    suspend fun createCollector(collector: Collector) = RetrofitBroker.createCollector(collector)


}