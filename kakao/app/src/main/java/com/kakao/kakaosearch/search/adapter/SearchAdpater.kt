package com.kakao.kakaosearch.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kakao.kakaosearch.R
import com.kakao.kakaosearch.repository.model.Document
import com.kakao.kakaosearch.search.adapter.viewholder.SearchViewHolder
import com.kakao.utils.inflate
import timber.log.Timber

class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSet = ArrayList<Document>()
    private val filterDataSet = ArrayList<Document>()
    private val tempList = ArrayList<Document>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SearchViewHolder(parent.inflate(R.layout.item_kakao_image))

    override fun getItemCount(): Int = dataSet.size

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
            filterDataSet.clear()
            filterDataSet.addAll(filter)
            this.dataSet.addAll(filterDataSet)
            notifyDataSetChanged()
        } else {
            this.dataSet.clear()
            this.dataSet.addAll(tempList)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchViewHolder).bind(dataSet[position])
    }

    class DiffCallback() : DiffUtil.ItemCallback<Document>() {
        override fun areItemsTheSame(
            oldItem: Document,
            newItem: Document
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Document,
            newItem: Document
        ): Boolean {
            return (oldItem.collection == newItem.collection) && (oldItem.thumbnail_url == newItem.thumbnail_url) && (oldItem.image_url == newItem.image_url) && (oldItem.width == newItem.width)
                    && (oldItem.height == newItem.height) && (oldItem.display_sitename == newItem.display_sitename) && (oldItem.doc_url == newItem.doc_url) && (oldItem.datetime == newItem.datetime)

        }
    }


}





