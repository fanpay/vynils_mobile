package com.uniandes.vynilsmobile.data.repository

import android.app.Application

import android.util.Log
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Comment
import com.uniandes.vynilsmobile.data.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentRepository(val application: Application) {

    suspend fun addComment(albumId: Int, comment: Comment) {
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiClient.comments.addComment(comment)

                if (response.isSuccessful) {
                    // El comentario se agregó correctamente
                    Log.v("CommentRepository", "Comentario agregado correctamente")
                } else {
                    throw ApiRequestException(
                        application.resources.getString(
                            R.string.error_save_comment
                        ), null
                    )
                }
            } catch (e: Throwable) {
                Log.e("CommentRepository", "Error agregando comentario desde la API: ${e.message}")
                throw ApiRequestException(
                    application.resources.getString(R.string.error_save_comment),
                    e
                )
            }
        }
    }

    // Otras funciones y lógica relacionada con comentarios pueden ir aquí si es necesario
}
