package br.edu.ifsp.scl.sdm.tripxp.use_cases

import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class TripUseCases {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getTrip(tripID: String, myCallback: (Trip) -> Unit) {
        val documentReference: DocumentReference = db.collection("trips").document(tripID)
        var trip = Trip()
        documentReference.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    trip = snapshot.toObject(Trip::class.java)!!
                    myCallback(trip)
                }
            }
    }
}