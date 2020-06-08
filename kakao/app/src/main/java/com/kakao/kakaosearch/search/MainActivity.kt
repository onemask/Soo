package com.kakao.kakaosearch.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakao.kakaosearch.R
import com.kakao.kakaosearch.base.BaseActivity
import com.kakao.kakaosearch.databinding.ActivityMainBinding
import com.kakao.kakaosearch.search.adapter.SearchAdapter
import com.kakao.kakaosearch.search.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>(
    layoutRes = R.layout.activity_main
) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val lists = mutableListOf("all")
    private val adapter = SearchAdapter()
    private val spinnerAdapter by lazy {
        ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            lists
        )
    }
    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this@MainActivity,
            viewModelFactory
        )[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAdapter()
        setBind()
        setupListener()
    }

    override fun setupViewModel() {
        binding.vm = viewModel
    }

    private fun setupAdapter() {
        binding.rv.adapter = adapter
        binding.spFilter.adapter = spinnerAdapter
    }

    private fun setBind() {
        viewModel.run {
            val lifecycleOwner = this@MainActivity
            searchResult.observe(lifecycleOwner, Observer {
                adapter.setData(it)
            })
            filterList.observe(lifecycleOwner, Observer {
                lists.run {
                    clear()
                    add("all")
                    addAll(it)
                }
                spinnerAdapter.notifyDataSetChanged()
            })
        }
    }

    private fun setupListener() {
        sp_filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setFilter(lists[position])
            }
        }

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) return
                adapter.itemCount.let {
                    if ((rv.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() + 2 >= it) {
                        viewModel.loadMore()
                    }
                }
            }
        })
    }
}
