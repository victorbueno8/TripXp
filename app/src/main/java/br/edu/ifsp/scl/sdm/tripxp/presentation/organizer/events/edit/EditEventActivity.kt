package br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_event.*

class EditEventActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var  db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        saveExcursionBt.setOnClickListener { view ->
            val trip = Trip(
                companyID = intent.getStringExtra("company") ?: "",
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