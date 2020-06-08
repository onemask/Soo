package com.kakao.kakaosearch.repository

import com.kakao.kakaosearch.repository.model.SearchResponse
import io.reactivex.Single

interface KaKaoRepository {

    fun getImageSearch(
        query: String,
        sort: String? = null,
        page: Int,
        size: Int? = null
    ): Single<SearchResponse>
}
