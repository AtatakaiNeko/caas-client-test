package com.inspirationdriven.caasclient.data.api

import com.inspirationdriven.caasclient.data.model.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/tags")
    suspend fun getTags(): Response<List<String>>

    @GET("api/cats")
    suspend fun getCats(@Query("tags") tags: String): Response<List<Cat>>

}