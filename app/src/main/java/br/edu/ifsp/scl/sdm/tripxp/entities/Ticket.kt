package br.edu.ifsp.scl.sdm.tripxp.entities

import com.google.firebase.firestore.Exclude
import java.text.SimpleDateFormat
import java.util.*

data class Ticket(
    @get:Exclude
    var id: String = "",
    val userID: String = "",
    val tripID: String = "",
    val tripName: String = "",
    val unitPrice: Double = 0.00,
    val qtd: Int = 1,
    val paymentMethod: String = "Crédito",
    val paymentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().time),
    val total: Double = 0.00
)
