package com.solomaticydl.moneymaker.utils

import android.content.Context
import com.solomaticydl.moneymaker.R

object MoneyUtil {
    fun moneyToString(context: Context, money: Double): String {
        return context.getString(R.string.format_money, String.format("%.2f", money))
    }
}