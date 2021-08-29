package br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_event.*
import kotlinx.android.synthetic.main.activity_edit_event_terms.*

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
        val documentReference: DocumentReference = db.collection("trips").document(eventID)
        documentReference.get().addOnSuccessListener { snapshot ->
            val event = snapshot.toObject(Trip::class.java)
            if (event != null) {
                companyID = event.companyID
                tripNameEt.setText(event.name)
                tripDescriptionEt.setText(event.description)
                eventCityEt.setText(event.eventCity)
                eventLocationEt.setText(event.eventAddress)
                dataInicioEtd.setText(event.eventStartDate)
                horaInicioEtt.setText(event.eventStartTime)
                dataFinalEtd.setText(event.eventEndDate)
                horaFinalEtt.setText(event.eventEndTime)
                meetingAddressEt.setText(event.meetingAddress)
                meetingCityEt.setText(event.meetingCity)
                meetingDateEt.setText(event.meetingDate)
                meetingHourEt.setText(event.meetingTime)
                meetingObservationsEt.setText(event.meetingObservation)
                returnAddressEt.setText(event.returnAddress)
                returnCityEt.setText(event.returnCity)
                returnDateEt.setText(event.returnDate)
                returnHourEt.setText(event.returnTime)
                returnObservationsEt.setText(event.returnObservation)
            }
        }

        saveExcursionBt.setOnClickListener { view ->
            val trip = Trip(
                companyID = companyID,
                name = tripNameEt.text.toString(),
                description = tripDescriptionEt.text.toString(),
                eventCity = eventCityEt.text.toString(),
                eventAddress = eventLocationEt.text.toString(),
                eventStartDate = dataInicioEtd.text.toString(),
                eventStartTime = horaInicioEtt.text.toString(),
                eventEndDate = dataFinalEtd.text.toString(),
                eventEndTime = horaFinalEtt.text.toString(),
                meetingAddress = meetingAddressEt.text.toString(),
                meetingCity = meetingCityEt.text.toString(),
                meetingDate = meetingDateEt.text.toString(),
                meetingTime = meetingHourEt.text.toString(),
                meetingObservation = meetingObservationsEt.text.toString(),
                returnAddress = returnAddressEt.text.toString(),
                returnCity = returnCityEt.text.toString(),
                returnDate = returnDateEt.text.toString(),
                returnTime = returnHourEt.text.toString(),
                returnObservation = returnObservationsEt.text.toString()
            )
            if (eventID.isNotEmpty()) {
                db.collection("trips").document(eventID)
                    .set(trip)
                    .addOnSuccessListener { document ->
                        Snackbar.make(view, "Viagem atualizada!", Snackbar.LENGTH_LONG).show()
                        finish()
                    }
                    .addOnFailureListener{ e ->
                        Snackbar.make(view, "Erro: " + e.message, Snackbar.LENGTH_LONG).show()
                    }
            } else {
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
}