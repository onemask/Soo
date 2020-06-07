package com.kakao.kakaosearch.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakao.kakaosearch.R
import com.kakao.kakaosearch.repository.KaKaoRepositoryImpl
import com.kakao.kakaosearch.search.adapter.SearchAdapter
import com.kakao.kakaosearch.search.vm.MainViewModel
import com.kakao.kakaosearch.search.vm.MainViewModelFactory
import com.kakao.utils.EndlessRecyclerOnScrollListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var kaKaoRepository: KaKaoRepositoryImpl

    private var searchKeyword: String = ""
    private val lists = mutableListOf("all")
    private val spinnerAdapter by lazy {
        ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            lists
        )
    }

    private val adapter by lazy { SearchAdapter() }

    private val viewModelFactory by lazy {
        MainViewModelFactory(kaKaoRepository)
    }

    private val viewModel by lazy {
        ViewModelProviders.of(
            this@MainActivity,
            viewModelFactory
        )[MainViewModel::class.java]

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAdapter()
        setupView()
        setBind()
    }

    private fun setupAdapter() {
        rv.adapter = adapter
        spinner_filter.adapter = spinnerAdapter

        rv.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(rv.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Timber.d("page $page")
                viewModel.run {
                    setPage(searchKeyword, page)
                }
            }
        })
    }

    private fun setBind() {
        viewModel.searchResult.observe(this, Observer {
            adapter.setData(it)
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

    private fun setupView() {
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.run {
                    if (this.isNotEmpty()) {
                        searchKeyword = this
                        viewModel.getSearch(searchkeyWord = searchKeyword)
                        spinner_filter.setSelection(0)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        spinner_filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Timber.d("${lists[position]}")
                adapter.filterData(lists[position])
            }

        }

    }


}
