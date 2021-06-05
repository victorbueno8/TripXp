package br.edu.ifsp.scl.sdm.tripxp.entities

import com.google.firebase.firestore.Exclude
import java.text.SimpleDateFormat
import java.util.*

data class Trip(
        @get:Exclude
        var id: String = "",
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
        ) {

        fun getStartEventDateTime(): String {
                return  this.eventStartDate + " " + this.eventStartTime
        }

        fun getEndEventDateTime(): String {
                return  this.eventEndDate + " " + this.eventEndTime
        }

        fun getEventLocation(): String {
                return this.eventAddress + ", " + this.eventCity
        }

        fun getMeetingDateTime() : String {
                return this.meetingDate + " " + this.meetingTime
        }

        fun getMeetingLocation() : String {
                return this.meetingAddress + ", " + this.meetingCity
        }

        fun returnDateTime() : String {
                return this.returnDate + " " + this.returnTime
        }

        fun getReturnLocation() : String {
                return this.returnAddress + ", " + this.returnCity
        }
}
