package com.kakao.kakaosearch.search.paging.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kakao.kakaosearch.R
import com.kakao.kakaosearch.repository.model.Document
import kotlinx.android.synthetic.main.item_kakao_image.view.*

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: Document) {
        with(itemView) {
            data.let {
                //img
                Glide.with(this.context)
                    .load(it.thumbnail_url)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(this.image)
            }
        }
    }
}