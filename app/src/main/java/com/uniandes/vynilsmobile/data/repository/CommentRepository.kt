package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import android.util.Log
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.database.CommentsDao
import com.uniandes.vynilsmobile.data.database.VinylRoomDatabase
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Comment
import com.uniandes.vynilsmobile.data.service.RetrofitBroker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentRepository(val application: Application) {

    private val commentsDao: CommentsDao by lazy {
        VinylRoomDatabase.getDatabase(application).commentsDao()
    }
    suspend fun addComment(albumId:Int,
                           comment: Comment,
                           onComplete: (resp: Comment) -> Unit,
                           onError: (error: Throwable) -> Unit) {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitBroker.addComment(
                    albumId,
                    comment,
                    onComplete = { response ->
                        CoroutineScope(Dispatchers.IO).launch {
                            insertCommentIntoDatabase(response)
                        }
                        onComplete(response)
                    },
                    onError = { error ->
                        onError(ApiRequestException(
                            application.resources.getString(
                                R.string.error_save_comment
                            ), error))
                        throw ApiRequestException(
                            application.resources.getString(
                                R.string.error_save_comment
                            ), error
                        )
                    }
                )

                return@withContext response
            } catch (e: Throwable) {
                Log.e("CommentRepository", "Error creating comment from API: ${e.message}")
                throw ApiRequestException(
                    application.resources.getString(R.string.error_save_comment),
                    e
                )
            }
        }
    }

    private suspend fun insertCommentIntoDatabase(comment: Comment) {
        commentsDao.insert(comment)
        Log.v("CommentRepository", "Inserted 1 comment into the local database. ID ${comment.id}")
    }
}
