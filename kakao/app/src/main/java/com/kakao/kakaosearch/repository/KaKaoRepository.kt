package com.kakao.kakaosearch.repository

import com.kakao.kakaosearch.repository.model.SearchResponse
import io.reactivex.Observable

interface KaKaoRepository {

    fun getImageSearch(
        query: String,
        sort: String? = null,
        page: Int,
        size: Int? = null
    ): Observable<SearchResponse>
}
