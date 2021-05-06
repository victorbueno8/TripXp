package br.edu.ifsp.scl.sdm.tripxp.entities

import java.util.*

data class User (
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