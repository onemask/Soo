package com.kakao.kakaosearch.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kakao.kakaosearch.R
import com.kakao.kakaosearch.databinding.ItemKakaoImageBinding
import com.kakao.kakaosearch.repository.model.Document
import com.kakao.kakaosearch.search.adapter.viewholder.SearchViewHolder
import timber.log.Timber

class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSet = ArrayList<Document>()
    private val filterDataSet = ArrayList<Document>()
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
        this.dataSet.clear()
        this.tempList.clear()
        data.let {
            this.dataSet.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun filterData(type: String) {
        tempList.addAll(dataSet)
        if (type != "all") {
            val filter = tempList.filter { it.collection == type }
            Timber.d("fitler $filter")
            this.dataSet.clear()
            filterDataSet.run {
                clear()
                addAll(filter)
            }
            this.dataSet.addAll(filterDataSet)
            notifyDataSetChanged()
        } else {
            this.dataSet.clear()
            this.dataSet.addAll(tempList)
            notifyDataSetChanged()
        }
    }

    fun addData(data: List<Document>?) {
        val startPosition = this.dataSet.size + 1
        data?.let {
            this.dataSet.addAll(data)
            notifyItemChanged(startPosition, this.dataSet.size + 1)
        }
    }

}





