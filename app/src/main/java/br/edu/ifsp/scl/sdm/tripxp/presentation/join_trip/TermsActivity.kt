package br.edu.ifsp.scl.sdm.tripxp.presentation.join_trip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import br.edu.ifsp.scl.sdm.tripxp.presentation.event.EventActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_terms.*

class TermsActivity : AppCompatActivity() {
    private lateinit var eventID: String
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var  db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        val cancelTermsButton : Button = findViewById(R.id.cancelTermsBt)
        val acceptTermsButton : Button = findViewById(R.id.acceptTermsBt)

        eventID = intent.getStringExtra("eventID") ?: ""
        if (eventID.isNotEmpty()) {
            val documentReference: DocumentReference = db.collection("trips").document(eventID)
            documentReference.get()
                .addOnSuccessListener { snapshot ->
                    val event = snapshot.toObject(Trip::class.java)
                    if (event != null) {
                        termsTv.text = event.terms
                    }
                }
        }

        cancelTermsButton.setOnClickListener { view ->
            val eventPage = Intent(this, EventActivity::class.java)
            eventPage.putExtra("eventID", eventID)
            eventPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(eventPage)
        }

        acceptTermsButton.setOnClickListener { view ->
            val paymentPage = Intent(this, PaymentMethodActivity::class.java)
            paymentPage.putExtra("eventID", eventID)
            paymentPage.putExtra("qtd", intent.getIntExtra("qtd", 0))
            paymentPage.putExtra("total", intent.getDoubleExtra("total", 0.00))
            paymentPage.putExtra("acceptedTerms", true)
            startActivity(paymentPage)
        }
    }
}