package br.edu.ifsp.scl.sdm.tripxp.presentation.search_trip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import br.edu.ifsp.scl.sdm.tripxp.presentation.event.EventActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips.EventListItemAdapter
import br.edu.ifsp.scl.sdm.tripxp.use_cases.ListTrips
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search_trips.*

class SearchTripsActivity : AppCompatActivity(), SearchListItemAdapter.OnItemClickListener {
    private var tripList: ArrayList<Trip> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_trips)

        searchTv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    search(newText)
                } else {
                    search("")
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    search(query)
                } else {
                    search("")
                }
                return false
            }
        })

        searchResultListRv.layoutManager = LinearLayoutManager(this)
        searchResultListRv.adapter = SearchListItemAdapter(tripList, this)
    }

    override fun onItemClick(position: Int) {
        val clickedTrip: Trip? = tripList?.get(position)

        if (clickedTrip != null) {
            val eventPage = Intent(this, EventActivity::class.java)
            eventPage.putExtra("eventID", clickedTrip.id)
            startActivity(eventPage)
        }
    }

    private fun search(s: String) {
        if (s.isNotEmpty()) {
            ListTrips().searchTrips(s) { trips ->
                tripList = trips
            }
        } else {
            tripList.clear()
        }
        searchResultListRv.adapter = SearchListItemAdapter(tripList, this)
    }


}