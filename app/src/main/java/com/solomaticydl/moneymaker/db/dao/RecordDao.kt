package com.solomaticydl.moneymaker.db.dao

import androidx.room.*
import com.solomaticydl.moneymaker.db.entity.Record

@Dao
interface RecordDao {

    @Query("SELECT * FROM record ORDER BY id DESC")
    fun getAllSortByDesc(): List<Record>

    @Insert
    fun insertAll(vararg records: Record)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: Record): Long

    @Delete
    suspend fun delete(record: Record): Int
}