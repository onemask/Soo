package com.kakao.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.kakao.kakaosearch.R

@BindingAdapter("bind:srcCompat")
fun srcCompat(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.mipmap.ic_launcher_round)
        .into(view)
}