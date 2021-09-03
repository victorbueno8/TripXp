package br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import br.edu.ifsp.scl.sdm.tripxp.util.DateFormat
import br.edu.ifsp.scl.sdm.tripxp.util.DatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_event.*

class EditEventActivity : AppCompatActivity() {
    private lateinit var eventID: String
    private lateinit var companyID: String
    private lateinit var auth: FirebaseAuth
    private lateinit var  db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        eventID = intent.getStringExtra("eventID") ?: ""
        companyID = intent.getStringExtra("company") ?: ""
        if (eventID.isNotEmpty()) {
            val documentReference: DocumentReference = db.collection("trips").document(eventID)
            documentReference.get().addOnSuccessListener { snapshot ->
                val event = snapshot.toObject(Trip::class.java)
                if (event != null) {
                    companyID = event.companyID
                    tripNameEt.setText(event.name)
                    tripDescriptionEt.setText(event.description)
                    eventCityEt.setText(event.eventCity)
                    eventLocationEt.setText(event.eventAddress)
                    dataInicioEtd.setText(DateFormat("dd/MM/yyyy HH:mm").toString(event.eventStart))
                    dataFinalEtd.setText(DateFormat("dd/MM/yyyy HH:mm").toString(event.eventEnd))
                    meetingAddressEt.setText(event.meetingAddress)
                    meetingCityEt.setText(event.meetingCity)
                    meetingDateEt.setText(DateFormat("dd/MM/yyyy HH:mm").toString(event.meetingTime))
                    meetingObservationsEt.setText(event.meetingObservation)
                    returnAddressEt.setText(event.returnAddress)
                    returnCityEt.setText(event.returnCity)
                    returnDateEt.setText(DateFormat("dd/MM/yyyy HH:mm").toString(event.returnTime))
                    returnObservationsEt.setText(event.returnObservation)
                }
            }
        }

        dataInicioEtd.setOnClickListener {
            val picker = DatePicker(this, dataInicioEtd)
            picker.pickDate()
        }

        dataFinalEtd.setOnClickListener {
            DatePicker(this, dataFinalEtd).pickDate()
        }

        meetingDateEt.setOnClickListener {
            DatePicker(this, meetingDateEt).pickDate()
        }

        returnDateEt.setOnClickListener {
            DatePicker(this, returnDateEt).pickDate()
        }

        saveExcursionBt.setOnClickListener { view ->

            if (eventID.isNotEmpty()) {
                val query = db.collection("trips").document(eventID)
                query.get().addOnSuccessListener { snapshot ->
                    val trip = setTrip(snapshot.toObject(Trip::class.java))
                    db.collection("trips").document(eventID)
                        .set(trip)
                        .addOnSuccessListener { document ->
                            Snackbar.make(view, "Viagem atualizada!", Snackbar.LENGTH_LONG).show()
                            finish()
                        }
                        .addOnFailureListener{ e ->
                            Snackbar.make(view, "Erro: " + e.message, Snackbar.LENGTH_LONG).show()
                        }
                }

            } else {
                val trip = setTrip(null)
                db.collection("trips")
                    .add(trip)
                    .addOnSuccessListener { document ->
                        Snackbar.make(view, "Nova viagem cadastrada!", Snackbar.LENGTH_LONG).show()
                        val imagePage = Intent(this, EditEventImageActivity::class.java)
                        imagePage.putExtra("eventID", document.id)
                        startActivity(imagePage)
                    }
                    .addOnFailureListener{ e ->
                        Snackbar.make(view, "Erro: " + e.message, Snackbar.LENGTH_LONG).show()
                    }
            }

        }
    }

    private fun setTrip(selectedTrip: Trip?) : Trip {
        val trip: Trip = selectedTrip ?: Trip()

        trip.companyID = companyID
        trip.userID = auth.currentUser!!.uid
        trip.name = tripNameEt.text.toString()
        trip.description = tripDescriptionEt.text.toString()
        trip.eventCity = eventCityEt.text.toString()
        trip.eventAddress = eventLocationEt.text.toString()
        trip.meetingAddress = meetingAddressEt.text.toString()
        trip.meetingCity = meetingCityEt.text.toString()
        trip.meetingObservation = meetingObservationsEt.text.toString()
        trip.returnAddress = returnAddressEt.text.toString()
        trip.returnCity = returnCityEt.text.toString()
        trip.returnObservation = returnObservationsEt.text.toString()
        if (dataInicioEtd.text.toString().isNotEmpty()) {
            trip.eventStart = DateFormat("dd/MM/yyyy HH:mm").toDate(dataInicioEtd.text.toString())
        }
        if (dataFinalEtd.text.toString().isNotEmpty()) {
            trip.eventEnd = DateFormat("dd/MM/yyyy HH:mm").toDate(dataFinalEtd.text.toString())
        }
        if (meetingDateEt.text.toString().isNotEmpty()) {
            trip.meetingTime = DateFormat("dd/MM/yyyy HH:mm").toDate(meetingDateEt.text.toString())
        }
        if (returnDateEt.text.toString().isNotEmpty()) {
            trip.returnTime = DateFormat("dd/MM/yyyy HH:mm").toDate(returnDateEt.text.toString())
        }

        return trip
    }
}