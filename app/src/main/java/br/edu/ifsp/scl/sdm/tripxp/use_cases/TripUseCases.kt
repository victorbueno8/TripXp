package br.edu.ifsp.scl.sdm.tripxp.use_cases

import android.util.Log
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import com.google.android.material.snackbar.Snackbar
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

    fun getMyCommingTrips(myCallback: (ArrayList<Trip>) -> Unit) {
        db.collectionGroup("tickets")
            .whereEqualTo("userID", auth.currentUser.uid)
            .get()
            .addOnFailureListener { e ->
                Log.d("ERROR", e.message.toString())
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val tripsDocs = task.result
                    val trips = ArrayList<Trip>()
                    tripsDocs?.forEach { doc ->
                        db.collection("trips").document(doc.getString("tripID")!!).get()
                            .addOnSuccessListener {
                                it.toObject(Trip::class.java).let { value ->
                                    if (value != null) {
                                        trips.add(value)
                                    }
                                }
                            }
                    }
                    myCallback(trips)
                }
            }
    }
}