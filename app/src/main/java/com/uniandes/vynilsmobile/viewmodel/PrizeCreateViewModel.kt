package com.uniandes.vynilsmobile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vynilsmobile.data.model.Prize
import com.uniandes.vynilsmobile.data.repository.PrizeRepository
import kotlinx.coroutines.launch

class PrizeCreateViewModel(application: Application) : AndroidViewModel(application) {

    private var prizesRepository: PrizeRepository

    private val _prize = MutableLiveData<Prize>()

    val prize: LiveData<Prize>
        get() = _prize

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
        prizesRepository = PrizeRepository(application)
    }

    fun savePrize(prize: Prize) {
        viewModelScope.launch {
            prizesRepository.createPrize(
                prize,
                onComplete = {
                    _prize.postValue(prize)
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
            if (modelClass.isAssignableFrom(PrizeCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PrizeCreateViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

