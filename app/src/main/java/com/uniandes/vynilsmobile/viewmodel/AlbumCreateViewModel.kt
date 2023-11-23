package com.uniandes.vynilsmobile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.repository.AlbumRepository
import kotlinx.coroutines.launch

class AlbumCreateViewModel(application: Application) : AndroidViewModel(application) {

    private var albumsRepository: AlbumRepository

    private val _album = MutableLiveData<Album>()

    val album: LiveData<Album>
        get() = _album

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    init {
        albumsRepository = AlbumRepository(application)
    }

    fun saveAlbum(album: Album) {
        viewModelScope.launch {
            albumsRepository.createAlbum(
                album,
                onComplete = {
                    _album.postValue(album)
                    _eventNetworkError.postValue(false)
                },
                onError = {
                    _eventNetworkError.postValue(true)
                }
            )
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumCreateViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

