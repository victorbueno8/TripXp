package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import br.edu.ifsp.scl.sdm.tripxp.R

class TermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        val cancelTermsButton : Button = findViewById(R.id.cancelTermsBt)
        val acceptTermsButton : Button = findViewById(R.id.acceptTermsBt)

        cancelTermsButton.setOnClickListener { view ->
            startActivity(Intent(this, EventActivity::class.java))
        }

        acceptTermsButton.setOnClickListener { view ->
            startActivity(Intent(this, PaymentMethodActivity::class.java))
        }
    }
}