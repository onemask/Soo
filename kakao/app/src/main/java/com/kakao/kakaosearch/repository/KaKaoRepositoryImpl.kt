package com.kakao.kakaosearch.repository

import com.kakao.constants.Constants.AUTHORIZATION
import com.kakao.kakaosearch.repository.model.SearchResponse
import io.reactivex.Observable

class KaKaoRepositoryImpl(private val apiService: KaKaoApiService) : KaKaoRepository {

    private val authorization = AUTHORIZATION

    override fun getImageSearch(
        query: String,
        sort: String?,
        page: Int,
        size: Int?
    ): Observable<SearchResponse> {
        return apiService.getImageSearch(authorization, query, sort, page, size)
    }

}