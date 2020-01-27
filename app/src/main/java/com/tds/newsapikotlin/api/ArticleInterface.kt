package com.tds.newsapikotlin.api

import com.tds.newsapikotlin.model.ArticleResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ArticleInterface{

    @Headers("X-Api-Key: 7579a65e3c27495aad3a5d764d4ccbc9")
    @GET("top-headlines")
    fun getArticle(
        @Query("country") country: String
    ):Call<ArticleResult>
}