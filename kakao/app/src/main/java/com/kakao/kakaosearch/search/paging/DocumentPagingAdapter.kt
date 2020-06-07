package com.kakao.kakaosearch.search.paging

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kakao.kakaosearch.R
import com.kakao.kakaosearch.repository.model.Document
import com.kakao.kakaosearch.search.paging.viewholder.SearchViewHolder
import com.kakao.utils.inflate


class DocumentPagingAdapter :
    PagedListAdapter<Document, RecyclerView.ViewHolder>(SearchDiffUtillCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(parent.inflate(R.layout.item_kakao_image))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as SearchViewHolder).bind(it)
        }
    }

}


class SearchDiffUtillCallback : DiffUtil.ItemCallback<Document>() {
    override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
        return oldItem == newItem
    }
}