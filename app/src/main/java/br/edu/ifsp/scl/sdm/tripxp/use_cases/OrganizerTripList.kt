package br.edu.ifsp.scl.sdm.tripxp.use_cases

import android.util.Log
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OrganizerTripList {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getCommingTrips(myCallback: (ArrayList<Trip>) -> Unit) {
        var companyID = ""

        db.collection("companies")
            .whereEqualTo("userID", auth.currentUser.uid)
            .get()
            .addOnSuccessListener { companies ->
                val company = companies.firstOrNull()
                if (company != null) companyID = company.id
            }

        db.collection("trips")
            .whereEqualTo("companyID", companyID)
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