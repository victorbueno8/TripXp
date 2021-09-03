package br.edu.ifsp.scl.sdm.tripxp.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import java.util.*

class DatePicker(val context: Context, val editText: EditText) : DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    init {
        val cur = Calendar.getInstance()
        day = cur.get(Calendar.DAY_OF_MONTH)
        month = cur.get(Calendar.MONTH)
        year = cur.get(Calendar.YEAR)
        hour = cur.get(Calendar.HOUR)
        minute = cur.get(Calendar.MINUTE)
    }

    fun pickDate() {
        DatePickerDialog(context, this, year, month, day).show()
    }

    private fun getDate() : Date {
        val cal = Calendar.getInstance()
        cal.set(year, month, day, hour, minute)
        return cal.time
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.day = dayOfMonth
        this.month = month
        this.year = year

        TimePickerDialog(context, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        this.hour = hourOfDay
        this.minute = minute

        editText.setText(DateFormat("dd/MM/yyyy HH:mm").toString(getDate()))
    }

}