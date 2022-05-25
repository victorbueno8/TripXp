package br.edu.ifsp.scl.sdm.tripxp.presentation.event.participants

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.User
import br.edu.ifsp.scl.sdm.tripxp.presentation.profile.UserProfileActivity
import br.edu.ifsp.scl.sdm.tripxp.use_cases.TripUseCases

/**
 * A fragment representing a list of Items.
 */
class ParticipantsFragment : Fragment(), ParticipantsRecyclerViewAdapter.OnItemClickListener {

    private var columnCount = 1
    private var eventID: String? = null
    private var userList: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_SECTION_NUMBER)
            eventID = it.getString(EVENT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_participant_list, container, false)

        // Set the adapter
        if (view is RecyclerView && eventID != null) {
            with(view) {
                layoutManager = LinearLayoutManager(activity)
                TripUseCases().getOrganizer(eventID!!) { organizer ->
                    TripUseCases().getParticipants(eventID!!) { users ->
                        userList = users
                        if (organizer != null) {
                            userList.add(0, organizer)
                        }
                        adapter = ParticipantsRecyclerViewAdapter(userList, this@ParticipantsFragment)
                    }
                }

            }
        }
        return view
    }

    override fun onItemClick(position: Int) {
        val user = userList.get(position)

        val profilePage = Intent(activity, UserProfileActivity::class.java)
        profilePage.putExtra("userID", user.id)
        startActivity(profilePage)
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private val EVENT_ID: String? = null

        @JvmStatic
        fun newInstance(sectionNumber: Int, eventID: String) =
            ParticipantsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putString(EVENT_ID, eventID)
                }
            }
    }
}