package com.kakao.kakaosearch.search.paging

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.kakao.kakaosearch.repository.KaKaoRepositoryImpl
import com.kakao.kakaosearch.repository.model.Document
import com.kakao.kakaosearch.search.vm.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SearchDataSource(
    private val kakaoRepository: KaKaoRepositoryImpl,
    private val mainViewModel: MainViewModel,
    private val filter: String
) : PageKeyedDataSource<Int, Document>() {

    private val disposable = CompositeDisposable()
    private val filters = mutableListOf<String>()

    companion object {
        const val PAGE_SIZE = 20
        const val START_PAGE = 1
    }

    fun clear() {
        disposable.clear()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Document>
    ) {
        if (mainViewModel.keyword.isNotEmpty()) {
            kakaoRepository.getImageSearch(
                query = mainViewModel.keyword,
                page = START_PAGE
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setFilter(it.documents)
                    val nextPage = if (it.meta.pageable_count < 20) START_PAGE else START_PAGE + 1
                    if (filter == "all") {
                        it.documents.let { callback.onResult(it, null, nextPage) }
                    } else
                        it.documents.filter { it.collection == filter }.let {
                            callback.onResult(it, null, nextPage)
                        }
                }, {
                    Timber.e("${it.printStackTrace()}")
                })
                .addTo(disposable)
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Document>) {
        if (mainViewModel.keyword.isNotEmpty()) {
            kakaoRepository.getImageSearch(
                query = mainViewModel.keyword,
                page = params.key
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setFilter(it.documents)
                    val nextPage = if (it.meta.pageable_count < 20) START_PAGE else START_PAGE + 1
                    if ( filter == "all") {
                        if (nextPage != 1) it.documents.let { callback.onResult(it, nextPage) }
                    } else {
                        if (nextPage != 1)
                            it.documents.filter { it.collection == filter }.let {
                                { callback.onResult(it, nextPage) }
                            }
                    }
                },
                    {
                        Timber.e("${it.printStackTrace()}")
                    })
                .addTo(disposable)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Document>) {

    }

    fun setFilter(documents: List<Document>) {
        filters.clear()
        filters.addAll(
            documents.map { it.collection }.distinct()
        )
        Timber.d(("filter $filters"))
    }

    fun getFilter(): MutableList<String> {
        return filters
    }

}

class SearchDataSorceFactory(
    val repository: KaKaoRepositoryImpl,
    val mainViewModel: MainViewModel,
    val filter: String
) :
    DataSource.Factory<Int, Document>() {

    private var dataSource: SearchDataSource? = null

    fun clear() = dataSource?.clear()

    fun invalidate() {
        dataSource?.clear()
        dataSource?.invalidate()
        dataSource?.getFilter()
        Timber.d("this ${dataSource?.getFilter()}")
    }

    override fun create(): DataSource<Int, Document> {
        dataSource = SearchDataSource(
            kakaoRepository = repository,
            mainViewModel = mainViewModel,
            filter = this.filter
        )
        return dataSource!!
    }

    fun getFilter(): MutableList<String> {
        Timber.d("rrr ${dataSource?.getFilter()}")
        return dataSource?.getFilter() ?: arrayListOf("all")
    }
}
