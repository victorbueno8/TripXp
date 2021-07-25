package br.edu.ifsp.scl.sdm.tripxp.entities

import com.google.firebase.firestore.Exclude
import java.util.*

data class PostComment (
    @get:Exclude
    var id: String = "",
    val userID: String = "",
    val postID: String = "",
    val text: String = "",
    val createdAt: Calendar = Calendar.getInstance(),
    val updatedAt: Calendar = Calendar.getInstance()
)