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

    private var _searchResult = MutableLiveData<List<Document>>()
    val searchResult: LiveData<List<Document>>
        get() = _searchResult

    private var _filter = MutableLiveData<List<String>>()
    val filter: LiveData<List<String>>
        get() = _filter

    private var _keyWord = MutableLiveData<String>()
    val keyWord: LiveData<String>
        get() = _keyWord

    private var page = MutableLiveData<Int>().apply { value = 1 }

    fun setPage(searchkeyWord: String,nextPage: Int) {
        page.value = nextPage
        getSearch(searchkeyWord)
    }

    fun getSearch(searchkeyWord: String) {
        kakaoRepository.getImageSearch(searchkeyWord, page = page.value ?: 1,size = PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _searchResult.postValue(it.documents)
                _filter.postValue(emptyList())
                _filter.postValue(
                    it.documents.map { it.collection }.distinct()
                )
            }, {
                Timber.e("${it.printStackTrace()}")
            })
            .addTo(disposable)
    }

    companion object{
        const val PAGE_SIZE = 20
    }

}