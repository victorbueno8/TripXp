package br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import br.edu.ifsp.scl.sdm.tripxp.presentation.event.EventActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_my_trips.*
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_SECTION_NUMBER = "section_number"
//private const val ARG_TRIP_LIST = "trip_list"

/**
 * A simple [Fragment] subclass.
 * Use the [MyTripsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyTripsFragment : Fragment(), EventListItemAdapter.OnItemClickListener {
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

            db.collection("users").document(auth.currentUser!!.uid).collection("tickets")
                .get()
                .addOnSuccessListener { snapshot ->
                    snapshot.forEach { ticketDoc ->
                        db.collection("trips").document(ticketDoc["tripID"] as String)
                            .get()
                            .addOnSuccessListener { tripDoc ->
                                val trip = tripDoc.toObject(Trip::class.java).apply {
                                    this?.id = tripDoc.id
                                    this?.ticketID = ticketDoc.id
                                    this?.ticketJoinDate = ticketDoc.getDate("paymentDate")
                                }
                                if (trip != null) {
                                    tripList.add(trip)
                                }
                                if (sectionNumber == 1) {
                                    tripList.sortByDescending { t -> t.ticketJoinDate }
                                } else {
                                    tripList = ArrayList(tripList.filter{ trip -> trip.meetingTime > Date() })
                                    tripList.sortBy { t -> t.meetingTime }
                                }
                                // set the custom adapter to the RecyclerView
                                adapter = EventListItemAdapter(tripList, this@MyTripsFragment)
                            }
                            .addOnFailureListener { e ->
                                Log.d("ERROR", e.message.toString())
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("ERROR", e.message.toString())
                }

//            db.collectionGroup("tickets")
//                .whereEqualTo("userID", auth.currentUser!!.uid)
//                .get()
//                .addOnFailureListener { e ->
//                    Log.d("ERROR", e.message.toString())
//                }
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val docs = task.result?.documents
//                        if (docs != null) {
//                            tripList.clear()
//                            for (snapshot: DocumentSnapshot in docs) {
//                                snapshot.reference.parent.parent?.let {
//                                    it.get().addOnSuccessListener { ok ->
//                                        val trip = ok.toObject(Trip::class.java).apply {
//                                            this?.id = ok.id
//                                            this?.ticketID = snapshot.id
//                                            this?.ticketJoinDate = snapshot.getDate("paymentDate")
//                                        }
//                                        if (trip != null) {
//                                            tripList.add(trip)
//                                        }
//                                        if (sectionNumber == 1) {
//                                            tripList.sortByDescending { t -> t.ticketJoinDate }
//                                        } else {
//                                            tripList = ArrayList(tripList.filter{ trip -> trip.meetingTime > Date() })
//                                            tripList.sortBy { t -> t.meetingTime }
//                                        }
//                                        // set the custom adapter to the RecyclerView
//                                        adapter = EventListItemAdapter(tripList, this@MyTripsFragment)
//                                    }
//                                }
//                            }
//
//                        }
//                    }
//                }
        }
    }

    override fun onItemClick(position: Int) {
        val clickedTrip: Trip = tripList.get(position)

        val eventPage = Intent(activity, EventActivity::class.java)
        eventPage.putExtra("eventID", clickedTrip.id)
        eventPage.putExtra("ticketID", clickedTrip.ticketID)
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
            MyTripsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    //putParcelableArrayList(ARG_TRIP_LIST, tripList)
                }
            }
    }
}