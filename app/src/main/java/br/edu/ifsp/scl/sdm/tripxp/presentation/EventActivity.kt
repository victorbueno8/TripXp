package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import android.os.Bundle
import android.util.EventLogTags
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import br.edu.ifsp.scl.sdm.tripxp.presentation.ui.main.SectionsPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var  db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val joinButton: Button = findViewById(R.id.joinBt)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val eventID = intent.getStringExtra("eventID") ?: ""

        val documentReference: DocumentReference = db.collection("trips").document(eventID)
        documentReference.get()
            .addOnSuccessListener { snapshot ->
                val event = snapshot.toObject(Trip::class.java)
                eventTitleLb.text = event?.name
            }

        joinButton.setOnClickListener { view ->
            startActivity(Intent(this, TermsActivity::class.java))
        }
    }
}