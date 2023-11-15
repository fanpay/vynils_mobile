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
import com.uniandes.vynilsmobile.data.model.Collector
import com.uniandes.vynilsmobile.data.repository.AlbumRepository
import com.uniandes.vynilsmobile.data.repository.CollectorRepository
import kotlinx.coroutines.launch


class CollectorViewModel(application: Application) :  AndroidViewModel(application) {
    var collectorsRepository = CollectorRepository(application)

    private val _collectors = MutableLiveData<List<Collector>>()

    val collectors: LiveData<List<Collector>>
        get() = _collectors

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
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        viewModelScope.launch {
            try {
                _collectors.value = collectorsRepository.getAllCollectors()
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
            catch(e:Exception) {
                Log.e("refreshDataFromNetwork", e.toString())
                _eventNetworkErrorMessage.value = "Error refreshDataFromNetwork $e"
                _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}