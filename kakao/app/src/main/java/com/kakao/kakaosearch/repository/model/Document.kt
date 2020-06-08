package com.kakao.kakaosearch.repository.model

import com.google.gson.annotations.SerializedName

data class Document(
    @SerializedName("collection")
    val collection: String,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("display_sitename")
    val display_sitename: String,
    @SerializedName("doc_url")
    val doc_url: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("thumbnail_url")
    val thumbnail_url: String,
    @SerializedName("width")
    val width: Int
)