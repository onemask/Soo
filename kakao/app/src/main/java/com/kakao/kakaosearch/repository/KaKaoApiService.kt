package com.kakao.kakaosearch.repository

import com.kakao.kakaosearch.repository.model.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KaKaoApiService {
    @GET("/v2/search/image")
    fun getImageSearch(
        @Header("Authorization") header: String,
        @Query("query") query: String,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int,
        @Query("size") size: Int? = null
    ): Single<SearchResponse>
}