package com.uniandes.vynilsmobile.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.repository.AlbumRepository
import kotlinx.coroutines.launch


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

    private var _eventNotDataFound = MutableLiveData(false)

    val eventNotDataFound: LiveData<Boolean>
        get() = _eventNotDataFound

    private var _isNotDataFoundShown = MutableLiveData(false)

    val isNotDataFoundShown: LiveData<Boolean>
        get() = _isNotDataFoundShown

    init {
        albumsRepository = AlbumRepository(application)
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        viewModelScope.launch{
            try {
                val data = albumsRepository.getAllAlbums()
                _albums.value = data

                checkDataAndSetFlags(data)
            } catch (e: Exception) {
                Log.e("refreshDataFromNetwork", e.toString())
                _eventNetworkError.postValue(true)
                _eventNotDataFound.postValue(false)
                _isNotDataFoundShown.postValue(false)
            }
        }
    }

    private fun checkDataAndSetFlags(data: List<Album>) {
        if (data.isEmpty()) {
            _eventNotDataFound.postValue(true)
            _isNotDataFoundShown.postValue(true)
        } else {
            _eventNotDataFound.postValue(false)
            _isNotDataFoundShown.postValue(false)
        }

        _eventNetworkError.postValue(false)
        _isNetworkErrorShown.postValue(false)
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
    fun onNotDataFoundShown() {
        _isNotDataFoundShown.value = true
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