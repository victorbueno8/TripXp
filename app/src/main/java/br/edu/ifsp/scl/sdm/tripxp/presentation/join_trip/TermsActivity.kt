package br.edu.ifsp.scl.sdm.tripxp.presentation.join_trip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.presentation.event.EventActivity

class TermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        val cancelTermsButton : Button = findViewById(R.id.cancelTermsBt)
        val acceptTermsButton : Button = findViewById(R.id.acceptTermsBt)

        val eventID = intent.getStringExtra("eventID") ?: ""

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