package br.edu.ifsp.scl.sdm.tripxp.entities

import java.text.SimpleDateFormat
import java.util.*

data class Trip(
        val companyID: String = "",
        val name: String = "",
        val description: String = "",
        val eventCity: String = "",
        val eventAddress: String = "",
        val eventStartDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().time),
        val eventStartTime: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().time),
        val eventEndDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().time),
        val eventEndTime: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().time),
        val meetingAddress: String = "",
        val meetingCity: String = "",
        val meetingDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().time),
        val meetingTime: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().time),
        val meetingObservation: String = "",
        val returnAddress: String = "",
        val returnCity: String = "",
        val returnDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().time),
        val returnTime: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().time),
        val returnObservation: String = ""
        )
