package com.kakao.kakaosearch.repository.model

data class Meta(
    val is_end: Boolean,
    val pageable_count: Long,
    val total_count: Long
)