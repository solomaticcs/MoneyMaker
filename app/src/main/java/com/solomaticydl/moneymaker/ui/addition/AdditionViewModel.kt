package com.solomaticydl.moneymaker.ui.addition

import androidx.lifecycle.MutableLiveData
import com.solomaticydl.moneymaker.db.entity.Record
import com.solomaticydl.moneymaker.repository.AdditionRepository
import com.solomaticydl.moneymaker.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AdditionViewModel(private val repository: AdditionRepository) : BaseViewModel() {

    private val _addDataResultLiveData = MutableLiveData<Result<Boolean>>()
    val addDataResultLiveData
        get() = _addDataResultLiveData

    fun addRecord(record: Record) = launchWithLoading {
        try {
            val addSuccess = withContext(Dispatchers.IO) {
                repository.add(record)
            }
            addDataResultLiveData.value = Result.success(addSuccess)
        } catch (e: Exception) {
            addDataResultLiveData.value = Result.failure(e)
        }
    }
}