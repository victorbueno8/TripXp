package br.edu.ifsp.scl.sdm.tripxp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class DateFormat(format: String) {
    private val formatter: SimpleDateFormat = SimpleDateFormat(format)

    fun toString(date: Date): String {
        return formatter.format(date)
    }

    fun toDate(string: String): Date? {
        return formatter.parse(string)
    }
}