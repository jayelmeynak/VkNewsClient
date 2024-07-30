package com.example.vknewsclient.data.network

import com.example.vknewsclient.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.get?v=5.199&source_ids=groups&filters=post")
    suspend fun loadNews(
        @Query("access_token") token: String
    ):NewsFeedResponseDto
}