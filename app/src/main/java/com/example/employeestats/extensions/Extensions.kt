package com.example.employeestats.extensions

import android.content.Context
import com.example.employeestats.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun String.toCamelCase(): String {
    return toCharArray().mapIndexed { index, c ->
        if (index == 0) c.toUpperCase()
        else c.toLowerCase()
    }.joinToString("")
}

private fun inYears(strDate: String): Int {
    val date: Date = toSimpleDateFormat(strDate)?.parse(strDate) ?: return -1
    return (TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - date.time) / 365).toInt()
}

private fun toSimpleDateFormat(date: String): SimpleDateFormat? {
    val regX1 = "[0-9]{2}+-[0-9]{2}+-[0-9]{4}+".toRegex()
    val regX2 = "[0-9]{4}+-[0-9]{2}+-[0-9]{2}+".toRegex()

    return when {
        date.matches(regX1) -> {
            SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        }
        date.matches(regX2) -> {
            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        }
        else -> null
    }
}

fun String?.toAge(context: Context): String {
    val years: Int = inYears(this ?: return "")
    return when {
        years % 10 == 0 -> {
            context.getString(R.string.let, years)
        }
        years % 10 == 1 -> {
            context.getString(R.string.year, years)
        }
        years % 10 in 2..4 -> {
            context.getString(R.string.years, years)
        }
        years % 10 in 5..9 -> {
            context.getString(R.string.let, years)
        }
        else -> ""
    }
}

fun String?.parseBirthday(): String {
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy г.", Locale.ENGLISH)
    val date = toSimpleDateFormat(this ?: return "--")?.parse(this) ?: return "--"
    return simpleDateFormat.format(date)
}

