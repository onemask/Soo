package com.kakao.kakaosearch.repository.model

import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("is_end")
    val is_end: Boolean,
    @SerializedName("pageable_count")
    val pageable_count: Long,
    @SerializedName("total_count")
    val total_count: Long
)