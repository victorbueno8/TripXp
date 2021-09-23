package br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import br.edu.ifsp.scl.sdm.tripxp.presentation.event.EventActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_event_terms.*

class EditEventTermsActivity : AppCompatActivity() {
    private lateinit var eventID: String
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var  db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event_terms)

        eventID = intent.getStringExtra("eventID") ?: ""
        val documentReference: DocumentReference = db.collection("trips").document(eventID)
        documentReference.get().addOnSuccessListener { snapshot ->
            val event = snapshot.toObject(Trip::class.java)
            if (event != null && event.terms.isNotEmpty()) {
                enterTermsTextEt.setText(event.terms)
            }
        }

        confirmTermsBt.setOnClickListener { view ->
            documentReference.update("terms", enterTermsTextEt.text.toString())
                .addOnSuccessListener {
                    Snackbar.make(view, "Regras da excursão foram salvas!", Snackbar.LENGTH_LONG).show()
                    if (intent.getStringExtra("method")  == "patch") {
                        val eventPage = Intent(this, EventActivity::class.java)
                        eventPage.putExtra("eventID", eventID)
                        eventPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(eventPage)
                    } else {
                        val ticketsPage = Intent(this, EditEventTicketsActivity::class.java)
                        ticketsPage.putExtra("eventID", eventID)
                        startActivity(ticketsPage)
                    }
                }
                .addOnFailureListener{ e ->
                    Snackbar.make(view, "Erro: " + e.message, Snackbar.LENGTH_LONG).show()
                }
        }
    }
}