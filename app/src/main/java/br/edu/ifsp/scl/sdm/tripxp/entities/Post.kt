package br.edu.ifsp.scl.sdm.tripxp.entities

import com.google.firebase.firestore.Exclude
import java.util.*
import kotlin.collections.ArrayList

data class Post (
    @get:Exclude
    var id: String = "",
    val userID: String = "",
    val text: String = "",
    val createdAt: Calendar = Calendar.getInstance(),
    val updatedAt: Calendar = Calendar.getInstance(),
    val comments: ArrayList<PostComment> = ArrayList()
)