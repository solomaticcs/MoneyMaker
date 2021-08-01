package com.solomaticydl.moneymaker.ui.base

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _loadingLiveData = MutableLiveData<Boolean>()

    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    fun launchWithLoading(block: suspend CoroutineScope.() -> Unit) {
        _loadingLiveData.value = true
        viewModelScope.launch {
            block()
        }
        _loadingLiveData.value = false
    }
}