package com.kakao.kakaosearch.search.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kakao.kakaosearch.base.BaseViewModel
import com.kakao.kakaosearch.repository.KaKaoRepositoryImpl
import com.kakao.kakaosearch.repository.model.Document
import com.kakao.kakaosearch.search.paging.SearchDataSorceFactory
import com.kakao.kakaosearch.search.paging.SearchDataSource
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(private val kakaoRepository: KaKaoRepositoryImpl) :
    BaseViewModel() {

    private var dataSourceFactory: SearchDataSorceFactory? = null

    var pageData: LiveData<PagedList<Document>>? = null
    var filter = MutableLiveData<MutableList<String>>().apply { listOf("all") }
    var keyword: String = ""

    //var filterType?: String = ""

    val type = mutableListOf("all")


    init {
        loadData()
    }

    fun updateData(keyword: String) {
        this.keyword = keyword
        dataSourceFactory?.invalidate()
        Timber.d("filter list ${dataSourceFactory?.getFilter()}")
        //?.let { filter.postValue(it) }
    }

    fun loadData(): LiveData<PagedList<Document>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(SearchDataSource.PAGE_SIZE)
            .setPageSize(SearchDataSource.PAGE_SIZE)
            .build()

        dataSourceFactory?.clear()
        dataSourceFactory = SearchDataSorceFactory(
            repository = kakaoRepository,
            mainViewModel = this,
            filter = "blog"
        )
        pageData = LivePagedListBuilder<Int, Document>(dataSourceFactory!!, config).build()

        return pageData!!
    }

    /*fun getSearch(keyword: String, page: Int = 1) {
        kakaoRepository.getImageSearch(keyword, page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _searchResult.postValue(it.documents)
            }, {
                Timber.e("${it.printStackTrace()}")
            })
            .addTo(disposable)
    }
*/

}