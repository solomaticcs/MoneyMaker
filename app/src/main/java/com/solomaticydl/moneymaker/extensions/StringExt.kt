package com.solomaticydl.moneymaker.extensions

import java.util.*

fun String.firstCharUpperCase(): String {
    return lowercase(Locale.getDefault()).replaceFirstChar {
        it.uppercase()
    }
}