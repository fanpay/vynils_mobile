package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import android.util.Log
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.database.PrizesDao
import com.uniandes.vynilsmobile.data.database.VinylRoomDatabase
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Prize
import com.uniandes.vynilsmobile.data.service.RetrofitBroker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrizeRepository(val application: Application) {
    private val prizesDao: PrizesDao by lazy {
        VinylRoomDatabase.getDatabase(application).prizesDao()
    }

    suspend fun createPrize(prize: Prize,
                            onComplete: (resp: Prize) -> Unit,
                            onError: (error: Throwable) -> Unit) {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitBroker.createPrize(
                    prize,
                    onComplete = { response ->
                        CoroutineScope(Dispatchers.IO).launch {
                            insertPrizeIntoDatabase(response)
                        }
                        onComplete(response)
                    },
                    onError = { error ->
                        onError(error)
                        throw ApiRequestException(
                            application.resources.getString(
                                R.string.error_retrieve_albums
                            ), error
                        )
                    }
                )

                return@withContext response
            } catch (e: Throwable) {
                Log.e("PrizeRepository", "Error creating prize from API: ${e.message}")
                throw ApiRequestException(
                    application.resources.getString(R.string.error_save_album),
                    e
                )
            }
        }
    }
    private suspend fun insertPrizeIntoDatabase(prize: Prize) {
        prizesDao.insert(prize)
        Log.v("PrizeRepository", "Inserted 1 prize into the local database. ID ${prize.id}")
    }
}