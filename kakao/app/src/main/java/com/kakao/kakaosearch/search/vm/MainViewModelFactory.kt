package com.kakao.kakaosearch.search.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kakao.kakaosearch.repository.KaKaoRepositoryImpl
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val repository: KaKaoRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(kakaoRepository = repository) as T
    }
}