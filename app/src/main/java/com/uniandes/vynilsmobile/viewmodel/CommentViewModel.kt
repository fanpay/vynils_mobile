package com.uniandes.vynilsmobile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uniandes.vynilsmobile.data.model.Comment
import com.uniandes.vynilsmobile.data.repository.CommentRepository
import kotlinx.coroutines.launch

class CommentViewModel(private val commentRepository: CommentRepository) : ViewModel() {

    fun addComment(albumId: Int, description: String, rating: String) {
        val comment = Comment(description, rating, albumId)

        viewModelScope.launch {
            try {
                commentRepository.addComment(albumId, comment)

            } catch (e: Exception) {

            }
        }
    }


}
