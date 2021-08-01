package com.solomaticydl.moneymaker.db

import androidx.room.TypeConverter
import com.solomaticydl.moneymaker.db.entity.RecordCategory
import com.solomaticydl.moneymaker.db.entity.text
import java.util.*

class Converter {

    @TypeConverter
    fun fromRecordCategory(recordCategory: RecordCategory): String {
        return recordCategory.text()
    }

    @TypeConverter
    fun toRecordCategory(recordCategoryStr: String): RecordCategory {
        return RecordCategory.valueOf(recordCategoryStr.uppercase(Locale.getDefault()))
    }
}