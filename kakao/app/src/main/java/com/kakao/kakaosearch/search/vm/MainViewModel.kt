package com.kakao.kakaosearch.search.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kakao.kakaosearch.base.BaseViewModel
import com.kakao.kakaosearch.repository.KaKaoRepositoryImpl
import com.kakao.kakaosearch.repository.model.Document
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(private val kakaoRepository: KaKaoRepositoryImpl) :
    BaseViewModel() {

    private val _searchResult = MutableLiveData<List<Document>>()
    val searchResult: LiveData<List<Document>>
        get() = _searchResult

    private val _filterList = MutableLiveData<List<String>>()
    val filterList: LiveData<List<String>>
        get() = _filterList

    private val itemList = mutableListOf<Document>()
    private var currentFilter: String? = null

    var searchKeyword = ""
    var currentPage: Long = 1L
    var totalPage: Long = 1L

    fun onClick(searchKeyword: String, isNext: Boolean) {
        if (searchKeyword.isNotEmpty())
            getSearch(searchKeyword, isNext)
    }

    fun loadMore() {
        if (currentPage < totalPage) {
            currentPage++
            getSearch(searchKeyword, true)
        }
    }

    fun setFilter(pickFilter: String) {
        currentFilter = pickFilter
        if (pickFilter != "all") {
            _searchResult.value = itemList.filter { it.collection == currentFilter }
        } else {
            _searchResult.value = itemList
        }
    }

    fun getSearch(searchKeyword: String, isNext: Boolean) {
        //false
        if (!isNext) {
            setupPage()
            itemList.clear()
        }

        kakaoRepository.getImageSearch(
            searchKeyword,
            page = currentPage.toInt(),
            size = PAGE_SIZE
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _loadingState.postValue(true) }
            .subscribe({ response ->
                totalPage = response.meta.pageable_count
                itemList.addAll(response.documents)
                _filterList.value = response.documents.map { it.collection }.distinct()
                _loadingState.postValue(false)
                if (currentFilter != "all") {
                    _searchResult.value = itemList.filter { it.collection == currentFilter }
                } else
                    _searchResult.value = itemList
            }, {
                Timber.e("${it.printStackTrace()}")
            })
            .addTo(disposable)
    }


    private fun setupPage() {
        currentPage = 1L
        totalPage = 1L
    }

    companion object {
        const val PAGE_SIZE = 20
    }

}