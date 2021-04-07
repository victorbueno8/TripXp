package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.tripxp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyTripsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_trips)

        val searchTripsFab: FloatingActionButton = findViewById(R.id.searchTripsFab)
        searchTripsFab.setOnClickListener { view ->
            startActivity(Intent(this, SearchTripsActivity::class.java))
        }
    }
}