package br.edu.ifsp.scl.sdm.tripxp.entities

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import java.text.SimpleDateFormat
import java.util.*

data class PostComment (
    @DocumentId
    var id: String = "",
    val user: User = User(),
    val postID: String = "",
    val text: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)