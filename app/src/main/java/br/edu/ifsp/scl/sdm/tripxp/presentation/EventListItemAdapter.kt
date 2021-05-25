package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip

class EventListItemAdapter(private val tripsList: ArrayList<Trip>) : RecyclerView.Adapter<EventListItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tripName: TextView = view.findViewById(R.id.tripNameTv)
        val tripDateTime: TextView = view.findViewById(R.id.tripDateTimeTv)
        val tripDestination : TextView = view.findViewById(R.id.tripDestinationTv)
        // val tripImage : ImageView = view.findViewById(R.id.tripImageIv)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_event, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val trip: Trip = tripsList[position]

        viewHolder.tripName.text = trip.name
        viewHolder.tripDateTime.text = trip.getMeetingDateTime()
        viewHolder.tripDestination.text = trip.eventCity
        // viewHolder.tripImage.setImageResource(R.drawable.ic_launcher_background)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = tripsList.size
}