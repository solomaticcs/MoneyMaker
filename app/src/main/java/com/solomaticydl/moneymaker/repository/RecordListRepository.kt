package com.solomaticydl.moneymaker.repository

import com.solomaticydl.moneymaker.db.dao.RecordDao
import com.solomaticydl.moneymaker.db.entity.Record

interface RecordListRepository {
    fun getRecordList(): List<Record>
    suspend fun deleteRecord(record: Record): Boolean
}

class RecordListRepositoryImpl(private val recordDao: RecordDao): RecordListRepository {
    override fun getRecordList(): List<Record> {
        return recordDao.getAllSortByDesc()
    }

    override suspend fun deleteRecord(record: Record): Boolean {
        return recordDao.delete(record) == 1
    }
}