package com.kakao.kakaosearch.repository

import com.kakao.kakaosearch.repository.model.SearchResponse
import io.reactivex.Observable

interface KaKaoRepository {

    fun getImageSearch(
        query: String,
        sort: String?,
        page: Int,
        size: Int?
    ): Observable<SearchResponse>
}
