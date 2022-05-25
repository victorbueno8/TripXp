package br.edu.ifsp.scl.sdm.tripxp.entities

import com.google.firebase.firestore.DocumentId
import java.util.*

data class User (
        @DocumentId
        var id: String = "",
        val email: String = "",
        val name: String = "",
        val fullname: String = "",
        val birthday: String = "",
        val city: String = "",
        val cep: String = "",
        val phone: String = "",
        val phone2: String = "",
        val profileImageUri: String = "",
        val userType: String = "user"
)