package com.solomaticydl.moneymaker.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solomaticydl.moneymaker.extensions.firstCharUpperCase

@Entity
data class Record(
    @ColumnInfo(name = "money") val money: Double,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "date_timestamp") val dateTimestamp: Long,
    @ColumnInfo(name = "category") val category: RecordCategory
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

enum class RecordCategory {
    BREAKFAST,
    LUNCH,
    DINNER,
    OTHERS
}

fun RecordCategory.text(): String {
    return name.firstCharUpperCase()
}
