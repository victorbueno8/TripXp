package br.edu.ifsp.scl.sdm.tripxp.use_cases

import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import br.edu.ifsp.scl.sdm.tripxp.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class UserUseCases {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getUser(userID: String = auth.currentUser!!.uid, myCallback: (User) -> Unit) {
        val documentReference: DocumentReference = db.collection("users").document(userID)
        var user = User()
        documentReference.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    user = snapshot.toObject(User::class.java)!!.apply { id = userID }
                    myCallback(user)
                }
            }
    }
}