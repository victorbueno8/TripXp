package br.edu.ifsp.scl.sdm.tripxp.presentation.event

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import br.edu.ifsp.scl.sdm.tripxp.entities.User
import br.edu.ifsp.scl.sdm.tripxp.presentation.join_trip.BuyTicketsActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.join_trip.TermsActivity
import br.edu.ifsp.scl.sdm.tripxp.use_cases.TripUseCases
import br.edu.ifsp.scl.sdm.tripxp.use_cases.UserUseCases
import br.edu.ifsp.scl.sdm.tripxp.util.NumberFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_buy_tickets.*
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.fragment_event_detail.*

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
        val numFormatter = NumberFormat()

        joinButton.visibility = View.GONE
        tabs.visibility = View.GONE
        UserUseCases().getUser { user ->
            TripUseCases().getParticipants(eventID) { participants ->
                if (user.userType == "user" && !participants.contains(user)) {
                    joinButton.visibility = View.VISIBLE
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
                    eventUnitPriceTv.text = "R$ ${numFormatter.format(event.ticketPrice)}"
                    eventNumberAvailableTicketsTv.text = event.ticketQtd.toString()
                    eventTitleLb.text = event.name
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
                }
            }

        joinButton.setOnClickListener { view ->
            val buyTicketsPage = Intent(this, BuyTicketsActivity::class.java)
            buyTicketsPage.putExtra("eventID", eventID)
            startActivity(buyTicketsPage)

        }
    }
}