package com.kakao.kakaosearch.repository.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("documents")
    val documents : List<Document>,
    @SerializedName("meta")
    val meta : Meta
)