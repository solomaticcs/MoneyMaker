package com.solomaticydl.moneymaker.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.solomaticydl.moneymaker.db.entity.Record
import com.solomaticydl.moneymaker.repository.RecordListRepository
import com.solomaticydl.moneymaker.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecordListViewModel(private val repository: RecordListRepository) : BaseViewModel() {

    private val _recordListLiveData = MutableLiveData<Result<List<Record>>>()
    val recordListLiveData: LiveData<Result<List<Record>>>
        get() = _recordListLiveData

    private val _deleteLiveData = MutableLiveData<Result<Boolean>>()
    val deleteLiveData: LiveData<Result<Boolean>>
        get() = _deleteLiveData

    fun requestRecordList() = launchWithLoading {
        try {
            val recordList = withContext(Dispatchers.IO) {
                repository.getRecordList()
            }
            _recordListLiveData.value = Result.success(recordList)
        } catch (e: Exception) {
            _recordListLiveData.value = Result.failure(e)
        }
    }

    fun deleteRecord(record: Record) = launchWithLoading {
        try {
            val deleteSuccess = withContext(Dispatchers.IO) {
                repository.deleteRecord(record)
            }
            val recordList: List<Record> = _recordListLiveData.value?.getOrNull()
                ?.toMutableList()?.apply {
                    remove(record)
                }
                ?: withContext(Dispatchers.IO) {
                    repository.getRecordList()
                }
            _recordListLiveData.value = Result.success(recordList)
            _deleteLiveData.value = Result.success(deleteSuccess)
        } catch (e: Exception) {
            _deleteLiveData.value = Result.failure(e)
        }
    }
}