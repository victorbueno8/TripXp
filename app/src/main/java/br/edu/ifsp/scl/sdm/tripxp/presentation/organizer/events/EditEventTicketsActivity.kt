package br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.presentation.event.EventActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_event_terms.*
import kotlinx.android.synthetic.main.activity_edit_event_tickets.*

class EditEventTicketsActivity : AppCompatActivity() {
    private lateinit var eventID: String
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var  db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event_tickets)

        eventID = intent.getStringExtra("eventID") ?: ""

        confirmTicketBt.setOnClickListener { view ->
            val documentReference: DocumentReference = db.collection("trips").document(eventID)
            documentReference.update(
                "ticketPrice", unitPriceEt.text.toString().toDouble(),
                "ticketQtd", ticketQtdEt.text.toString().toDouble())
                .addOnSuccessListener {
                    Snackbar.make(view, "Dados de pagamento da excursão foram salvas!", Snackbar.LENGTH_LONG).show()
                    val eventPage = Intent(this, EventActivity::class.java)
                    eventPage.putExtra("eventID", eventID)
                    eventPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(eventPage)
                }
                .addOnFailureListener{ e ->
                    Snackbar.make(view, "Erro: " + e.message, Snackbar.LENGTH_LONG).show()
                }
        }
    }
}