package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.database.ArtistsDao
import com.uniandes.vynilsmobile.data.database.VinylRoomDatabase
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Artist
import com.uniandes.vynilsmobile.data.service.RetrofitBroker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistRepository(val application: Application) {
    private val artistsDao: ArtistsDao by lazy {
        VinylRoomDatabase.getDatabase(application).artistsDao()
    }
    suspend fun getAllArtists(): List<Artist> {
        return withContext(Dispatchers.IO) {

            try {
                val cached = artistsDao.getArtists()

                if (!isNetworkAvailable() && cached.isNotEmpty()) {
                    Log.v("ArtistRepository", "No Internet. Retrieving cached artists. ${cached.size} found.")
                    return@withContext cached
                }

                if (!isNetworkAvailable() && cached.isEmpty()) {
                    Log.v("ArtistRepository", "No Internet. No cached data was found. Retrieving empty list.")
                    return@withContext cached
                }

                if (cached.isNotEmpty()) {
                    Log.v("ArtistRepository", "Retrieving cached artists. ${cached.size} found.")
                    return@withContext cached
                }

                var artists: List<Artist> = emptyList()

                RetrofitBroker.getArtists(
                    onComplete = { response ->
                        artists = response
                        CoroutineScope(Dispatchers.IO).launch {
                            insertArtistsIntoDatabase(artists)
                        }
                    },
                    onError = { error ->
                        throw ApiRequestException(
                            application.resources.getString(
                                R.string.error_retrieve_artists
                            ), error
                        )
                    }
                )

                return@withContext artists
            } catch (e: Throwable) {
                Log.e("ArtistRepository", "Error retrieving artists from API: ${e.message}")
                throw ApiRequestException(
                    application.resources.getString(R.string.error_retrieve_artists),
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

    private suspend fun insertArtistsIntoDatabase(artists: List<Artist>) {
        artistsDao.insertAll(artists)
        Log.v("ArtistRepository", "Inserted ${artists.size} artists into the local database.")
    }



}