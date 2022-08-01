package com.example.workmangertask_1.retrofitApi

import com.example.workmangertask_1.model.Posts
import retrofit2.Response
import retrofit2.http.GET

interface PostApi {
    @GET("posts")
    suspend fun getPost(
    ): Response<List<Posts>>


}