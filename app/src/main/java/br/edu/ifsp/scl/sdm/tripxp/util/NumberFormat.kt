package br.edu.ifsp.scl.sdm.tripxp.util

import kotlinx.android.synthetic.main.activity_payment_confirm.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class NumberFormat {
    private val df: DecimalFormat = DecimalFormat()
    private val symbols = DecimalFormatSymbols()

    init {
        symbols.decimalSeparator = ','
        symbols.groupingSeparator = ' ';
        df.decimalFormatSymbols = symbols
    }

    fun format(number: Double): String {
        return df.format(number)
    }

    fun parse(number: String): Double {
        val formated = df.parse(number.removePrefix("R$ ")) ?: 0.0
        return formated.toDouble()
    }
}