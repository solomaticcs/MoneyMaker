package com.solomaticydl.moneymaker.repository

import com.solomaticydl.moneymaker.db.dao.RecordDao
import com.solomaticydl.moneymaker.db.entity.Record

interface AdditionRepository {
    suspend fun add(record: Record): Boolean
}

class AdditionRepositoryImpl(private val recordDao: RecordDao) : AdditionRepository {
    override suspend fun add(record: Record): Boolean {
        return recordDao.insert(record) != -1L
    }
}