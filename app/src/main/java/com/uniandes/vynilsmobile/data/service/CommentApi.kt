package com.uniandes.vynilsmobile.data.service
import com.uniandes.vynilsmobile.data.model.Comment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentApi {

    @POST("albums/{albumId}/comments")
    suspend fun addComment(
        @Path("albumId") albumId: Int,
        @Body comment: Comment): Response<Comment>
}