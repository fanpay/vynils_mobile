package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import com.uniandes.vynilsmobile.data.model.Comment
import com.uniandes.vynilsmobile.data.service.RetrofitBroker

class CommentsRepository(val application: Application) {

    suspend fun getAllComments(): List<Comment> = RetrofitBroker.getAllComments()

}