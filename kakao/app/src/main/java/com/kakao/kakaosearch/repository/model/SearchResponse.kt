package com.kakao.kakaosearch.repository.model

data class SearchResponse(
    val documents : List<Document>,
    val meta : Meta
)