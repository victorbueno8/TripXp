package br.edu.ifsp.scl.sdm.tripxp.entities

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

data class Post (
    @DocumentId
    var id: String = "",
    val user: User = User(),
    val tripID: String = "",
    val text: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val comments: ArrayList<PostComment> = ArrayList()
)