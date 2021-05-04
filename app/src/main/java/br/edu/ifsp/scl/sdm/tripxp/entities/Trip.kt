package br.edu.ifsp.scl.sdm.tripxp.entities

import java.util.*

data class Trip(
        val name: String = "",
        val description: String = "",
        val eventLocation: String = "",
        val eventAddress: String = "",
        val eventStart: Calendar = Calendar.getInstance(),
        val eventEnd: Calendar = Calendar.getInstance(),
        val meetingAddress: String = "",
        val meetingCity: String = "",
        val meetingObservation: String = "",
        val meetingDateTime: Calendar = Calendar.getInstance(),
        val returnAddress: String = "",
        val returnDateTime: Calendar = Calendar.getInstance(),
        val returnObservation: String = ""
        )
