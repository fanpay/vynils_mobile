package com.uniandes.vynilsmobile.data.service
import com.uniandes.vynilsmobile.data.model.Comment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CommentApi {

    @POST("comments")
    suspend fun addComment(@Body comment: Comment): Response<Comment>
}