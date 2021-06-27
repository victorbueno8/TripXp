package br.edu.ifsp.scl.sdm.tripxp.presentation.join_trip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.presentation.event.EventActivity
import kotlinx.android.synthetic.main.activity_join_trip_confirm.*

class JoinTripConfirmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_trip_confirm)

        Log.d("OK", intent.getStringExtra("ticketID").toString())

        continueBt.setOnClickListener { view ->
            val eventPage = Intent(this, EventActivity::class.java)
            eventPage.putExtra("eventID", intent.getStringExtra("eventID") ?: "")
            eventPage.putExtra("ticketID", intent.getStringExtra("ticketID") ?: "")
            eventPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(eventPage)
        }
    }
}