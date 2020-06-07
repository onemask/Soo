package com.kakao.kakaosearch.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kakao.kakaosearch.R
import com.kakao.kakaosearch.repository.KaKaoRepositoryImpl
import com.kakao.kakaosearch.search.paging.DocumentPagingAdapter
import com.kakao.kakaosearch.search.vm.MainViewModel
import com.kakao.kakaosearch.search.vm.MainViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var kaKaoRepository: KaKaoRepositoryImpl

    private val pagingAdapter by lazy { DocumentPagingAdapter() }
    private val spinnerAdapter by lazy {
        ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            listOf("all")
        )
    }

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
        rv.adapter = pagingAdapter
        spinner_filter.adapter = spinnerAdapter
    }

    private fun setBind() {
        viewModel.pageData?.observe(this, Observer {
            pagingAdapter.submitList(it)
        })

        viewModel.filter.observe(this, Observer {
            spinnerAdapter.addAll(it)
            spinnerAdapter.notifyDataSetChanged()
        })
    }

    private fun setupView() {
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.run {
                    if (this.isNotEmpty()) {
                        Timber.d("keyword $this")
                        viewModel.keyword = this
                        viewModel.updateData(this)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }


}

private fun androidx.appcompat.widget.SearchView.setOnQueryTextListener() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}
