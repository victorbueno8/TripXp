package br.edu.ifsp.scl.sdm.tripxp.entities

import br.edu.ifsp.scl.sdm.tripxp.util.DateFormat
import com.google.firebase.firestore.Exclude
import java.text.SimpleDateFormat
import java.util.*

data class Trip(
        @get:Exclude
        var id: String = "",
        var companyID: String = "",
        var userID: String = "",
        var eventImageUri: String = "",
        var name: String = "",
        var description: String = "",
        var eventCity: String = "",
        var eventAddress: String = "",
        var eventStart: Date = Date(),
        var eventEnd: Date = Date(),
        var meetingAddress: String = "",
        var meetingCity: String = "",
        var meetingTime: Date = Date(),
        var meetingObservation: String = "",
        var returnAddress: String = "",
        var returnCity: String = "",
        var returnTime: Date = Date(),
        var returnObservation: String = "",
        var terms: String = "",
        var ticketPrice: Double = 0.00,
        var ticketQtd: Int = 1,
        val createdAt: Date = Date(),
        val updatedAt: Date = Date(),
        @get:Exclude @set:Exclude
        var ticketID: String? = null,
        @get:Exclude @set:Exclude
        var ticketJoinDate: Date? = null
        ) {

        fun getStartEventDateTime(): String {
                return DateFormat("dd/MM/yyyy HH:mm").toString(this.eventStart)
        }

        fun getEndEventDateTime(): String {
                return DateFormat("dd/MM/yyyy HH:mm").toString(this.eventEnd)
        }

        fun getEventLocation(): String {
                return this.eventAddress + ", " + this.eventCity
        }

        fun getMeetingDateTime() : String {
                return DateFormat("dd/MM/yyyy HH:mm").toString(this.meetingTime)
        }

        fun getMeetingLocation() : String {
                return this.meetingAddress + ", " + this.meetingCity
        }

        fun returnDateTime() : String {
                return DateFormat("dd/MM/yyyy HH:mm").toString(this.returnTime)
        }

        fun getReturnLocation() : String {
                return this.returnAddress + ", " + this.returnCity
        }
}
