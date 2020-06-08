package com.kakao.kakaosearch.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kakao.kakaosearch.R
import com.kakao.kakaosearch.databinding.ItemKakaoImageBinding
import com.kakao.kakaosearch.repository.model.Document
import com.kakao.kakaosearch.search.adapter.viewholder.SearchViewHolder

class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSet = ArrayList<Document>()
    private val tempList = ArrayList<Document>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val item = DataBindingUtil.inflate<ItemKakaoImageBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_kakao_image,
            parent,
            false
        )
        return SearchViewHolder(item)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchViewHolder).bind(dataSet[position])
    }

    fun setData(data: List<Document>) {
        dataSet.clear()
        tempList.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }
}





