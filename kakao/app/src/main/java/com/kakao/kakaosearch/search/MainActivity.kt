package com.kakao.kakaosearch.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakao.kakaosearch.R
import com.kakao.kakaosearch.base.BaseActivity
import com.kakao.kakaosearch.databinding.ActivityMainBinding
import com.kakao.kakaosearch.repository.KaKaoRepositoryImpl
import com.kakao.kakaosearch.search.adapter.SearchAdapter
import com.kakao.kakaosearch.search.vm.MainViewModel
import com.kakao.kakaosearch.search.vm.MainViewModelFactory
import com.kakao.utils.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    @Inject
    lateinit var kaKaoRepository: KaKaoRepositoryImpl
    private val spinnerAdapter by lazy {
        ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            lists
        )
    }
    private val adapter by lazy { SearchAdapter() }
    private val viewModelFactory by lazy { MainViewModelFactory(kaKaoRepository) }
    private var searchKeyword: String = ""
    private val lists = mutableListOf("all")

    override fun onStart() {
        super.onStart()
        setupAdapter()
        setBind()
        setupListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }

    override val viewModel by lazy {
        ViewModelProviders.of(
            this@MainActivity,
            viewModelFactory
        )[MainViewModel::class.java]

    }

    private fun setupAdapter() {
        rv.adapter = adapter
        sp_filter.adapter = spinnerAdapter
    }

    private fun setBind() {
        viewModel.searchResult.observe(this, Observer {
            if (viewModel.currentPage == 1L)
                adapter.setData(it)
            else
                adapter.addData(it)
        })

        viewModel.filter.observe(this, Observer {
            lists.apply {
                clear()
                add("all")
                addAll(it)
            }
            spinnerAdapter.notifyDataSetChanged()
        })
    }

    private fun setupListener() {
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.run {
                    if (this.isNotEmpty()) {
                        searchKeyword = this
                        viewModel.getSearch(searchKeyword = searchKeyword)
                        sp_filter.setSelection(0)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        sp_filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                adapter.filterData(lists[position])
            }
        }

        rv.addOnScrollListener(object : EndlessRecyclerOnScrollListener(rv.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Timber.d("page $page")
                viewModel.currentPage = page.toLong()
                if (viewModel.currentPage < viewModel.totalPage) {
                    viewModel.getSearch(searchKeyword)
                    viewModel.currentPage++
                }
            }
        })
    }
}
