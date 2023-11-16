package com.uniandes.vynilsmobile.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vynilsmobile.data.database.VinylRoomDatabase
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.repository.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AlbumViewModel(application: Application) :  AndroidViewModel(application) {

    var albumsRepository: AlbumRepository

    private val _albums = MutableLiveData<List<Album>>()

    val albums: LiveData<List<Album>>
        get() = _albums

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private var _eventNetworkErrorMessage = MutableLiveData<String>()
    val eventNetworkErrorMessage: LiveData<String>
        get() = _eventNetworkErrorMessage

    init {
        val albumDao = VinylRoomDatabase.getDatabase(application).albumsDao()
        albumsRepository = AlbumRepository(application, albumDao)
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        viewModelScope.launch (Dispatchers.IO){
            try{
                withContext(Dispatchers.Main){
                    val data = albumsRepository.getAllAlbums()
                    _albums.value = data
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
            catch (e:Exception){
                Log.e("refreshDataFromNetwork", e.toString())
                _eventNetworkErrorMessage.postValue("Error refreshDataFromNetwork $e")
                _eventNetworkError.postValue(true)
            }

        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
         override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}