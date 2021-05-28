package br.edu.ifsp.scl.sdm.tripxp.entities

import com.google.firebase.firestore.Exclude

data class Company(
        @get:Exclude
        var id: String = "",
        val userID: String = "",
        val name: String = "",
        val document: String = "",
        val city: String = "",
        val tripServices: String = "",
        val description: String = "",
)