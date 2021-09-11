package br.edu.ifsp.scl.sdm.tripxp.presentation.event

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import br.edu.ifsp.scl.sdm.tripxp.presentation.LoginActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.UserProfileActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.join_trip.BuyTicketsActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips.MyTripsActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.ManageEventsActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.edit.EditEventActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.edit.EditEventImageActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.edit.EditEventTermsActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.edit.EditEventTicketsActivity
import br.edu.ifsp.scl.sdm.tripxp.use_cases.TripUseCases
import br.edu.ifsp.scl.sdm.tripxp.use_cases.UserUseCases
import br.edu.ifsp.scl.sdm.tripxp.util.NumberFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.fragment_event_detail.*
import kotlinx.android.synthetic.main.toolbar.*

class EventActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var  db: FirebaseFirestore

    private lateinit var eventID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        eventID = intent.getStringExtra("eventID") ?: ""

    }

    override fun onResume() {
        super.onResume()

        joinBt.visibility = View.GONE
        tabs.visibility = View.GONE
        UserUseCases().getUser { user ->
            TripUseCases().getParticipants(eventID) { participants ->
                if (user.userType == "user" && !participants.contains(user)) {
                    joinBt.visibility = View.VISIBLE
                }
                if (user.userType == "organizer" || participants.contains(user)) {
                    tabs.visibility = View.VISIBLE
                }
            }
        }

        val documentReference: DocumentReference = db.collection("trips").document(eventID)
        documentReference.get()
            .addOnSuccessListener { snapshot ->
                val event = snapshot.toObject(Trip::class.java)
                if (event != null) {
                    this.toolbar.title = event.name
                    eventUnitPriceTv.text = "R$ ${NumberFormat().format(event.ticketPrice)}"
                    eventNumberAvailableTicketsTv.text = event.ticketQtd.toString()
                    eventDescriptionTv.text = event.description
                    destinationAddressTv.text = event.getEventLocation()
                    eventStartTimeTv.text = event.getStartEventDateTime()
                    eventEndTimeTv.text = event.getEndEventDateTime()
                    gatheringAddressTv.text = event.getMeetingLocation()
                    gatheringTimestampTv.text = event.getMeetingDateTime()
                    gatheringObservationsTv.text = event.meetingObservation
                    returnAddressTv.text = event.getReturnLocation()
                    returnTimestampTv.text = event.returnDateTime()
                    returnObservationsTv.text = event.returnObservation

                    if (event.eventImageUri.isNotEmpty()) {
                        Picasso.get().load(event.eventImageUri).into(tripImageIv)
                    }
                }
            }

        joinBt.setOnClickListener { view ->
            val buyTicketsPage = Intent(this, BuyTicketsActivity::class.java)
            buyTicketsPage.putExtra("eventID", eventID)
            startActivity(buyTicketsPage)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        UserUseCases().getUser { user ->
            if (user.userType == "organizer") {
                menuInflater.inflate(R.menu.manage_trip_menu, menu)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.editDetailsMenuOption -> {
            val intent = Intent(this, EditEventActivity::class.java)
            intent.putExtra("eventID", eventID)
            intent.putExtra("method","patch")
            startActivity(intent)
            true
        }
        R.id.editImageMenuOption -> {
            val intent = Intent(this, EditEventImageActivity::class.java)
            intent.putExtra("eventID", eventID)
            intent.putExtra("method","patch")
            startActivity(intent)
            true
        }
        R.id.editTermsMenuOption -> {
            val intent = Intent(this, EditEventTermsActivity::class.java)
            intent.putExtra("eventID", eventID)
            intent.putExtra("method","patch")
            startActivity(intent)
            true
        }
        R.id.editTicketsMenuOption -> {
            val intent = Intent(this, EditEventTicketsActivity::class.java)
            intent.putExtra("eventID", eventID)
            intent.putExtra("method","patch")
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        UserUseCases().getUser { user ->
            val tripsPage = if (user.userType == "organizer") {
                Intent(this, ManageEventsActivity::class.java)
            } else {
                Intent(this, MyTripsActivity::class.java)
            }
            tripsPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(tripsPage)
            finish()
        }
    }
}