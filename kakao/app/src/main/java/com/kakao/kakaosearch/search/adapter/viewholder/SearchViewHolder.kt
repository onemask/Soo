package com.kakao.kakaosearch.search.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.kakao.kakaosearch.databinding.ItemKakaoImageBinding
import com.kakao.kakaosearch.repository.model.Document

class SearchViewHolder(private val binding: ItemKakaoImageBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Document) {
        binding.document = data
    }
}
