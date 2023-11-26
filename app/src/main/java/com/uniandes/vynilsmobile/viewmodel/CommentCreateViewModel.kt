package com.uniandes.vynilsmobile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vynilsmobile.data.model.Comment
import com.uniandes.vynilsmobile.data.repository.CommentRepository
import kotlinx.coroutines.launch

class CommentCreateViewModel(application: Application) : AndroidViewModel(application) {

    private var commentsRepository: CommentRepository

    private val _comment = MutableLiveData<Comment>()

    val comment: LiveData<Comment>
        get() = _comment

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
        commentsRepository = CommentRepository(application)
    }

    fun addComment(albumId:Int, comment: Comment) {
        viewModelScope.launch {
            commentsRepository.addComment(
                albumId,
                comment,
                onComplete = {
                    _comment.postValue(comment)
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
            if (modelClass.isAssignableFrom(CommentCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CommentCreateViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
