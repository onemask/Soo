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

    var currentPage: Long = 1L
    var totalPage: Long = 1L

    fun getSearch(searchKeyword: String) {
        kakaoRepository.getImageSearch(searchKeyword, page = currentPage.toInt(), size = PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _loadingState.postValue(true) }
            .subscribe({
                _searchResult.postValue(it.documents)
                _loadingState.postValue(false)
                _filter.run {
                    postValue(emptyList())
                    postValue(it.documents.map { it.collection }.distinct())
                }
                totalPage = it.meta.pageable_count
            }, {
                Timber.e("${it.printStackTrace()}")
            })
            .addTo(disposable)
    }

    companion object {
        const val PAGE_SIZE = 20
    }

}