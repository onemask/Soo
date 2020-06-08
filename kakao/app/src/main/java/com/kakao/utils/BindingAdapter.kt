package com.kakao.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.kakao.kakaosearch.R
import kotlinx.android.synthetic.main.item_kakao_image.view.*

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("srcCompat")
    fun srcCompat(view: ImageView, url: String) {
        Glide.with(view.context).load(url).thumbnail(0.1f)
            .placeholder(R.mipmap.ic_launcher_round)
            .into(view.image)
    }
}