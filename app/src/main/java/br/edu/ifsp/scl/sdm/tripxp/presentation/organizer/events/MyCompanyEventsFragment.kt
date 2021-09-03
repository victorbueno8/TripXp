package br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import br.edu.ifsp.scl.sdm.tripxp.presentation.event.EventActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips.EventListItemAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_my_trips.*

private const val ARG_SECTION_NUMBER = "section_number"
/**
 * A fragment representing a list of Items.
 */
class MyCompanyEventsFragment : Fragment(), EventListItemAdapter.OnItemClickListener {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var sectionNumber: Int? = null
    private var viewContext = activity?.applicationContext
    private var tripList: ArrayList<Trip> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewContext = activity?.applicationContext

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        arguments?.let {
            sectionNumber = it.getInt(ARG_SECTION_NUMBER)
            // tripList = it.get(ARG_TRIP_LIST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_trips, container, false)
    }

    override fun onResume() {
        super.onResume()
        tripListRv.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = EventListItemAdapter(tripList, this@MyCompanyEventsFragment)

            db.collection("companies")
                .whereEqualTo("userID", auth.currentUser!!.uid)
                .get()
                .addOnSuccessListener { companies ->
                    val company = companies.firstOrNull()

                    if (company != null) {
                        db.collection("trips")
                            .whereEqualTo("companyID", company.id)
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    tripList.clear()
                                    task.result?.forEach { trip ->
                                        tripList.add(trip.toObject(Trip::class.java).apply { id = trip.id })
                                        (adapter as EventListItemAdapter).notifyDataSetChanged()
                                    }
                                }
                            }
                    }
                }


        }
    }

    override fun onItemClick(position: Int) {
        val clickedTrip: Trip = tripList.get(position)

        val eventPage = Intent(activity, EventActivity::class.java)
        eventPage.putExtra("eventID", clickedTrip.id)
        startActivity(eventPage)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyTripsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(sectionNumber: Int) =
            MyCompanyEventsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    //putParcelableArrayList(ARG_TRIP_LIST, tripList)
                }
            }
    }
}