package com.solomaticydl.moneymaker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.solomaticydl.moneymaker.db.dao.RecordDao
import com.solomaticydl.moneymaker.db.entity.Record
import com.solomaticydl.moneymaker.db.entity.RecordCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Record::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao

    companion object {
        private const val DATABASE_NAME = "records.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(roomDatabaseCallback).build()
                INSTANCE = instance
                instance
            }
        }

        private val roomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val recordDao = INSTANCE?.recordDao() ?: return@launch
                    val recordList = recordDao.getAllSortByDesc()
                    if (recordList.isNotEmpty()) {
                        return@launch
                    }
                    // Add sample records if record list is empty
                    recordDao.insertAll(
                        Record(
                            50.toDouble(),
                            "hamburger",
                            1627292400000,
                            RecordCategory.BREAKFAST
                        ),
                        Record(
                            100.toDouble(),
                            "mcdonald chicken nuggets",
                            1627302600000,
                            RecordCategory.LUNCH
                        ),
                        Record(
                            30.toDouble(),
                            "pancake",
                            1627313400000,
                            RecordCategory.OTHERS
                        ),
                        Record(
                            45.toDouble(),
                            "egg pancake roll",
                            1627379130000,
                            RecordCategory.BREAKFAST
                        ),
                        Record(
                            80.toDouble(),
                            "omelette",
                            1627388700000,
                            RecordCategory.LUNCH
                        ),
                        Record(
                            120.toDouble(),
                            "chicken",
                            1627414200000,
                            RecordCategory.DINNER
                        ),
                    )
                }
            }
        }
    }
}