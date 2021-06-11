package br.edu.ifsp.scl.sdm.tripxp.use_cases

import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class ListTrips {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun searchTrips(search: String, myCallback: (ArrayList<Trip>) -> Unit) {
        db.collection("trips")
            .whereGreaterThanOrEqualTo("name", search)
            .whereLessThanOrEqualTo("name", search + '\uf8ff')
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val trips = ArrayList<Trip>()
                    task.result?.forEach { trip ->
                        trips.add(trip.toObject(Trip::class.java).apply { id = trip.id })
                    }
                    myCallback(trips)
                }
            }
    }
}