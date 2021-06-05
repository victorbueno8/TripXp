package br.edu.ifsp.scl.sdm.tripxp.entities

import com.google.firebase.firestore.Exclude
import java.util.*

data class User (
        @get:Exclude
        var id: String = "",
        val email: String = "",
        val name: String = "",
        val fullname: String = "",
        val birthday: String = "",
        val city: String = "",
        val cep: String = "",
        val phone: String = "",
        val phone2: String = "",
        val userType: String = "user"
)